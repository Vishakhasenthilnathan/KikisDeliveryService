package org.eve.services;

import org.eve.entities.DeliveryInfo;
import org.eve.entities.DeliveryVehicle;
import org.eve.entities.DeliveryVehicles;
import org.eve.entities.Package;
import org.eve.repositories.interfaces.IDeliveryInfoRepository;

import java.util.*;
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

    public List<DeliveryInfo> getDeliveryCostInfoForAllPackages(int baseDeliveryCost) {
        saveDeliveryCostInfo(baseDeliveryCost);
        return deliveryInfoRepository.getAll();
    }

    public List<DeliveryInfo> getDeliveryTimeInfoForAllPackages(int baseDeliveryCost) {
        saveDeliveryCostInfo(baseDeliveryCost);
        saveDeliveryTimeInfo();
        return deliveryInfoRepository.getAll();
    }

    private void saveDeliveryCostInfo(int baseDeliveryCost) {
        HashMap<String, Package> packageList = packageService.getAllPackages();
        for (Package pack : packageList.values()) {
            DeliveryInfo deliveryCostInfo = calculateDeliveryCostInfo(baseDeliveryCost, pack);
            deliveryInfoRepository.save(deliveryCostInfo);
        }
    }

    private DeliveryInfo calculateDeliveryCostInfo(int baseDeliveryCost, Package deliveryPackage) {
        int totalDeliveryCost = baseDeliveryCost + (deliveryPackage.getWeightInKgs() * BASE_WEIGHT_CHARGE) + (deliveryPackage.getDistanceInKms() * BASE_DISTANCE_CHARGE);
        int discountedCost = discountService.getDiscountPriceForPackage(deliveryPackage, totalDeliveryCost);
        int totalDeliveryCostIncludingDiscount = totalDeliveryCost - discountedCost;
        return new DeliveryInfo(baseDeliveryCost, deliveryPackage.getId(), totalDeliveryCost, discountedCost, totalDeliveryCostIncludingDiscount);
    }

    private void saveDeliveryTimeInfo() {
        HashMap<String, Package> packageList = packageService.getAllPackages();
        DeliveryVehicles deliveryVehicleInfo = deliveryVehiclesService.getDeliveryVehicleInfo();
        List<List<String>> possibleCombinationsOfPackageForDelivery = getPossibleCombinationsOfPackageForDelivery(new ArrayList<>(packageList.values()), deliveryVehicleInfo.getMaxLoadCapacity());
        List<String> packagesAlreadyDelivered = new ArrayList<>();
        int numberOfPendingPackages = packageList.size();

        while (numberOfPendingPackages > 0) {
            List<String> packageThatCanBeDelivered = getPackagesThatCanBeAddedForDelivery(possibleCombinationsOfPackageForDelivery, packagesAlreadyDelivered);
            Optional<DeliveryVehicle> availableVehicle = deliveryVehicleInfo.getDeliveryVehicleList().stream().filter(DeliveryVehicle::isAvailable).findFirst();
            if (availableVehicle.isPresent()) {
                double timeAtWhichVehicleWillBeAvailableNext = Double.MIN_VALUE;

                for (String packageID : packageThatCanBeDelivered) {
                    Package _package = packageList.get(packageID);
                    double estimatedHoursToDeliver = calculateDeliveryHoursForPackage(_package, deliveryVehicleInfo.getMaxSpeed());
                    double estimatedDeliveryTime = estimatedHoursToDeliver + deliveryVehicleInfo.getCurrentTime();
                    double timeAtWhichCurrentVehicleWillBeAvailable = deliveryVehicleInfo.getCurrentTime() + estimatedHoursToDeliver * 2.0;
                    timeAtWhichVehicleWillBeAvailableNext = Math.max(timeAtWhichVehicleWillBeAvailableNext, timeAtWhichCurrentVehicleWillBeAvailable);

                    deliveryInfoRepository.updateDeliveryInfoWithEstimatedTime(_package.getId(), estimatedDeliveryTime);
                }

                deliveryVehiclesService.updateDeliveryVehicleAvailability(availableVehicle.get().getId(), false, packageThatCanBeDelivered, timeAtWhichVehicleWillBeAvailableNext);
                packagesAlreadyDelivered.addAll(packageThatCanBeDelivered);
                numberOfPendingPackages -= packageThatCanBeDelivered.size();

            } else {

                DeliveryVehicle vehicleArrivingFirst = deliveryVehiclesService.getVehicleCompletingDeliveryFirst();
                double earliestTime = vehicleArrivingFirst.getTimeAtWhichDeliveryCompletes();
                double timeAtWhichVehicleWillBeAvailable = deliveryVehicleInfo.getCurrentTime() + earliestTime;
                deliveryVehicleInfo.setCurrentTime(timeAtWhichVehicleWillBeAvailable);

                deliveryVehiclesService.updateDeliveryVehicleAvailability(vehicleArrivingFirst.getId(), true, new ArrayList<>(), 0);
            }
        }
    }

    private double calculateDeliveryHoursForPackage(Package _package, int maxSpeed) {
        int distanceToBeTravelled = _package.getDistanceInKms();
        return Math.floor(((double) distanceToBeTravelled / maxSpeed) *100.0)/100.0;
    }

    private List<String> getPackagesThatCanBeAddedForDelivery(List<List<String>> possibleCombinationsOfPackageForDelivery, List<String> packagesAlreadyAddedForDelivery) {
        return possibleCombinationsOfPackageForDelivery.stream()
                .filter(combination -> combination.stream().noneMatch(packagesAlreadyAddedForDelivery::contains))
                .distinct()
                .collect(Collectors.toList()).stream().findFirst().get();
    }

    private List<List<String>> getPossibleCombinationsOfPackageForDelivery(List<Package> packageList, int maxLoad) {
        List<List<String>> possiblePackageCombinations = new ArrayList<>();

        List<Package> packageSortedByWeight = packageList.stream().sorted(Comparator.comparingInt(Package::getWeightInKgs)).collect(Collectors.toList());
        findAllPackageCombinations(0, 0, possiblePackageCombinations, new ArrayList<>(), packageSortedByWeight, maxLoad);

        possiblePackageCombinations.sort((o1, o2) -> {
            int totalWeightA = 0, totalWeightB = 0;
            List<Package> package1 = packageService.getPackageByPackageIds(o1);
            List<Package> package2 = packageService.getPackageByPackageIds(o2);
            for (Package p : package1) totalWeightA += p.getWeightInKgs();
            for (Package p : package2) totalWeightB += p.getWeightInKgs();
            return Integer.compare(totalWeightB, totalWeightA);
        });

        return possiblePackageCombinations;
    }

    private void findAllPackageCombinations(int index, int sum, List<List<String>> possiblePackageCombinations, List<String> currentCombination, List<Package> packageSortedByWeight, int maxLoad) {
        if (sum <= maxLoad) {
            possiblePackageCombinations.add(new ArrayList<>(currentCombination));
        }
        for (int i = index; i < packageSortedByWeight.size(); i++) {
            Package currentPackage = packageSortedByWeight.get(i);
            int currentWeightSum = sum + currentPackage.getWeightInKgs();
            if (currentWeightSum <= maxLoad) {
                currentCombination.add(currentPackage.getId());
                findAllPackageCombinations(i + 1, currentWeightSum, possiblePackageCombinations, currentCombination, packageSortedByWeight, maxLoad);
                currentCombination.remove(currentCombination.size() - 1);
            }
        }
    }
}
