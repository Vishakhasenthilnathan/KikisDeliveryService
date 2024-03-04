package org.eve.commands;

import org.eve.entities.DeliveryInfo;
import org.eve.entities.OfferCode;
import org.eve.services.DeliveryInfoService;
import org.eve.services.DeliveryVehiclesService;
import org.eve.services.PackageService;

import java.util.List;

import static org.eve.entities.OfferCode.parseOfferCode;

public class CalculateDeliveryTimeCommand implements ICommand{
    private final DeliveryInfoService deliveryInfoService;
    private final DeliveryVehiclesService deliveryVehiclesService;
    private final PackageService packageService;

    public CalculateDeliveryTimeCommand(DeliveryInfoService deliveryInfoService, PackageService packageService, DeliveryVehiclesService deliveryVehiclesService){
        this.deliveryInfoService = deliveryInfoService;
        this.packageService = packageService;
        this.deliveryVehiclesService = deliveryVehiclesService;
    }
    @Override
    public void invoke(List<String> tokens) {

        int baseDeliveryCost = Integer.parseInt(tokens.get(0));
        int numberOfPackages = Integer.parseInt(tokens.get(1));
        int token = 2;
        while (numberOfPackages > 0) {
            String packageId = tokens.get(token++);
            int weight = Integer.parseInt(tokens.get(token++));
            int distance = Integer.parseInt(tokens.get(token++));
            OfferCode offerCode = parseOfferCode(tokens.get(token++));

            packageService.createPackage(packageId, weight, distance, offerCode);

            numberOfPackages--;
        }

        int numberOfVehicles = Integer.parseInt(tokens.get(token++));
        int maxSpeed  = Integer.parseInt(tokens.get(token++));
        int maxCapacityLoad  = Integer.parseInt(tokens.get(token++));

        deliveryVehiclesService.registerDeliveryVehiclesInfo(numberOfVehicles,maxCapacityLoad,maxSpeed);

        List<DeliveryInfo> deliveryInfo = deliveryInfoService.getDeliveryTimeInfoForAllPackages(baseDeliveryCost);
        deliveryInfo.forEach(System.out::println);

    }
}
