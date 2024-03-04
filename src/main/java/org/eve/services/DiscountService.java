package org.eve.services;

import org.eve.entities.Discount;
import org.eve.entities.Package;
import org.eve.repositories.interfaces.IDiscountRepository;

import java.util.Optional;

public class DiscountService {
    private final IDiscountRepository discountRepository;

    public DiscountService(IDiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public int getDiscountPriceForPackage(Package packageForDelivery, int totalDeliveryCost) {
        int discountPercent = calculateDiscountPercent(packageForDelivery);
        return (discountPercent * totalDeliveryCost) / 100;
    }

    public int calculateDiscountPercent(Package packageForDelivery) {
        if (isPackageEligibleForDiscount(packageForDelivery)) {
            return discountRepository.findByOfferCode(packageForDelivery.getOfferCode()).get().getOfferedPercentage();
        } else {
            return 0;
        }
    }
    private boolean isPackageEligibleForDiscount(Package packageForDelivery) {
        Optional<Discount> discountInfo = discountRepository.findByOfferCode(packageForDelivery.getOfferCode());
        if (discountInfo.isPresent()) {
            Discount discount = discountInfo.get();
            boolean isDistanceEligibleForOffer = packageForDelivery.getDistanceInKms() >= discount.getMinDistance() && packageForDelivery.getDistanceInKms() <= discount.getMaxDistance();
            boolean isWeightEligibleForOffer = packageForDelivery.getWeightInKgs() >= discount.getMinWeight() && packageForDelivery.getWeightInKgs() <= discount.getMaxWeight();
            return isDistanceEligibleForOffer && isWeightEligibleForOffer;
        } else return false;
    }
}
