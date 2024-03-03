package org.eve.services;

import org.eve.entities.DeliveryInfo;
import org.eve.entities.DeliveryVehicle;
import org.eve.entities.DeliveryVehicles;
import org.eve.entities.Package;
import org.eve.repositories.interfaces.IDeliveryInfoRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeliveryInfoService {
    private final PackageService packageService;
    private final DiscountService discountService;
    private final IDeliveryInfoRepository deliveryInfoRepository;
    private final DeliveryVehiclesService deliveryVehiclesService;

    private static final int BASE_WEIGHT_CHARGE = 10;
    private static final int BASE_DISTANCE_CHARGE = 5;

    public DeliveryInfoService(PackageService packageService, DiscountService discountService, DeliveryVehiclesService deliveryVehiclesService, IDeliveryInfoRepository deliveryInfoRepository) {
        this.packageService = packageService;
        this.discountService = discountService;
        this.deliveryVehiclesService = deliveryVehiclesService;
        this.deliveryInfoRepository = deliveryInfoRepository;
    }

    public List<DeliveryInfo> getDeliveryTimeInfoForAllPackages(int baseDeliveryCost) {
        List<Package> packageList = packageService.getAllPackages();
        for (Package pack : packageList) {
            DeliveryInfo deliveryInfo = calculateDeliveryCostInfo(baseDeliveryCost, pack);
            deliveryInfoRepository.save(deliveryInfo);
        }
        calculateEstimatedDeliveryTime(packageList);
        return deliveryInfoRepository.findAll();
    }

    private void calculateEstimatedDeliveryTime(List<Package> packageList) {
        DeliveryVehicles deliveryVehicles = deliveryVehiclesService.getDeliveryVehicleInfo();
        List<List<String>> possibleCombinationsOfPackageForDelivery = getPossibleCombinationsOfPackageForDelivery(packageList, deliveryVehicles);
        List<String> packagesAlreadyAddedForDelivery = new ArrayList<>();
        int i = 0;
        DeliveryVehicles deliveryVehiclesInfo = deliveryVehiclesService.getDeliveryVehicleInfo();

        while (deliveryVehiclesInfo.getNumberOfPendingPackages()>0) {
            List<String> packagesThatCanBeAddedForDelivery = getPackagesThatCanBeAddedForDelivery(possibleCombinationsOfPackageForDelivery,packagesAlreadyAddedForDelivery);
            Optional<DeliveryVehicle> deliveryVehicle = deliveryVehiclesInfo.getDeliveryVehicleList().stream().filter(DeliveryVehicle::isAvailable).findFirst();
            if (deliveryVehicle.isPresent() && packagesThatCanBeAddedForDelivery!=null) {
                double timeAtWhichVehicleWillBeAvailableNext = Double.MIN_VALUE;
                for (String packageID : packagesThatCanBeAddedForDelivery) {
                    Package _package = packageService.getPackage(packageID);
                    int distanceToBeTravelled = _package.getDistanceInKms();
                    int speedAtWhichItCanBeDelivered = deliveryVehicles.getMaxSpeed();
                    double estimatedHoursToDeliver = (double) distanceToBeTravelled / speedAtWhichItCanBeDelivered;
                    deliveryInfoRepository.updateDeliveryInfoWithEstimatedTime(_package.getId(), estimatedHoursToDeliver + deliveryVehicles.getCurrentTime());
                    timeAtWhichVehicleWillBeAvailableNext = Math.max(timeAtWhichVehicleWillBeAvailableNext, estimatedHoursToDeliver * 2);
                }
                deliveryVehiclesService.updateDeliveryVehicle(deliveryVehicle.get().getId(), false, packagesThatCanBeAddedForDelivery, timeAtWhichVehicleWillBeAvailableNext);
                packagesThatCanBeAddedForDelivery.forEach(x-> packagesAlreadyAddedForDelivery.add(x));
                packageList.removeIf(x -> packagesAlreadyAddedForDelivery.contains(x.getId()));
                var pending = deliveryVehiclesInfo.getNumberOfPendingPackages() - packagesThatCanBeAddedForDelivery.size();
                deliveryVehiclesInfo.setNumberOfPendingPackages(pending);
                deliveryVehiclesInfo.setNumberOfAvailableVehicles(deliveryVehiclesInfo.getNumberOfAvailableVehicles()-1);
            } else {
                double earliestTime = deliveryVehiclesService.getVehicleCompletingDeliveryFirst().getTimeAtWhichDeliveryCompletes();
                deliveryVehicles.setCurrentTime(deliveryVehicles.getCurrentTime() + earliestTime);
                deliveryVehiclesService.updateDeliveryVehicle(deliveryVehiclesService.getVehicleCompletingDeliveryFirst().getId(), true, new ArrayList<>(), 0);
            }
        }
    }

    private List<String> getPackagesThatCanBeAddedForDelivery(List<List<String>> possibleCombinationsOfPackageForDelivery, List<String> packagesAlreadyAddedForDelivery) {
        return possibleCombinationsOfPackageForDelivery.stream()
                .filter(combination -> combination.stream().noneMatch(packagesAlreadyAddedForDelivery::contains))
//                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList()).stream().findFirst().get();
    }

    public List<List<String>> getPossibleCombinationsOfPackageForDelivery(List<Package> packageList, DeliveryVehicles deliveryVehicles) {
        int maxLoad = deliveryVehicles.getMaxLoadCapacity();
        List<List<String>> possiblePackageCombinations = new ArrayList<>();
        List<Package> packageSortedByWeight = packageList.stream().sorted(Comparator.comparingInt(Package::getWeightInKgs)).collect(Collectors.toList());
        findAllPackageCombinations(0, packageSortedByWeight, maxLoad, possiblePackageCombinations, new ArrayList<>(), 0);
        possiblePackageCombinations.sort((o1, o2) -> {
            int totalWeightA = 0, totalWeightB = 0;
            List<Package> package1 = packageService.getPackageByPackageIds(o1);
            List<Package> package2 = packageService.getPackageByPackageIds(o2);
            for (Package p : package1) totalWeightA += p.getWeightInKgs();
            for (Package p : package2) totalWeightB += p.getWeightInKgs();
            return Integer.compare(totalWeightB, totalWeightA);
        });
        System.out.println(possiblePackageCombinations);

        return possiblePackageCombinations;
    }

    private void findAllPackageCombinations(int index, List<Package> packageList, int maxLoad, List<List<String>> eligiblePackages, List<String> currentCombination, int sum) {
        if (sum <= maxLoad) {
            eligiblePackages.add(new ArrayList<>(currentCombination));
        }
        for (int i = index; i < packageList.size(); i++) {
            if (sum + packageList.get(i).getWeightInKgs() <= maxLoad) {
                currentCombination.add(packageList.get(i).getId());
                findAllPackageCombinations(i + 1, packageList, maxLoad, eligiblePackages, currentCombination, sum + packageList.get(i).getWeightInKgs());
                currentCombination.remove(currentCombination.size() - 1);
            }
        }
    }


    public List<DeliveryInfo> getDeliveryCostInfoForAllPackages(int baseDeliveryCost) {
        List<Package> packageList = packageService.getAllPackages();
        for (Package pack : packageList) {
            DeliveryInfo deliveryCostInfo = calculateDeliveryCostInfo(baseDeliveryCost, pack);
            deliveryInfoRepository.save(deliveryCostInfo);
        }

        return deliveryInfoRepository.findAll();
    }

    private DeliveryInfo calculateDeliveryCostInfo(int baseDeliveryCost, Package pack) {
        int totalDeliveryCost = baseDeliveryCost + (pack.getWeightInKgs() * BASE_WEIGHT_CHARGE) + (pack.getDistanceInKms() * BASE_DISTANCE_CHARGE);
        int discountPercent = discountService.getDiscountPercentForPackage(pack);
        int discountedCost = discountService.calculateDiscountPriceForPackage(totalDeliveryCost, discountPercent);
        int totalDeliveryCostIncludingDiscount = totalDeliveryCost - discountedCost;
        return new DeliveryInfo(baseDeliveryCost, pack.getId(), totalDeliveryCost, discountedCost, totalDeliveryCostIncludingDiscount);
    }
}
