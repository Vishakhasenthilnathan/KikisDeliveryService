package org.eve.commands;

import org.eve.entities.DeliveryInfo;
import org.eve.entities.OfferCode;
import org.eve.services.DeliveryInfoService;
import org.eve.services.PackageService;

import java.util.List;

import static org.eve.entities.OfferCode.parseOfferCode;

public class CalculateDeliveryCostCommand implements ICommand {
    private final DeliveryInfoService deliveryInfoService;
    private final PackageService packageService;

    public CalculateDeliveryCostCommand(DeliveryInfoService deliveryInfoService, PackageService packageService) {
        this.deliveryInfoService = deliveryInfoService;
        this.packageService = packageService;
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
        List<DeliveryInfo> deliveryInfo = deliveryInfoService.getDeliveryCostInfoForAllPackages(baseDeliveryCost);
        deliveryInfo.forEach(System.out::println);
    }
}
