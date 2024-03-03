package org.eve.services;

import org.eve.entities.Discount;
import org.eve.entities.OfferCode;
import org.eve.entities.Package;
import org.eve.repositories.interfaces.IDiscountRepository;

import java.util.Optional;

public class DiscountService {
    private final IDiscountRepository discountRepository;

    public DiscountService(IDiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public boolean isPackageEligibleForDiscount(Package packageForDelivery, OfferCode offerCode) {
        Optional<Discount> discountInfo = discountRepository.findByOfferCode(offerCode);
        if (discountInfo.isPresent()) {
            Discount discount = discountInfo.get();
            boolean isDistanceEligibleForOffer = packageForDelivery.getDistanceInKms() >= discount.getMinDistance() && packageForDelivery.getDistanceInKms() <= discount.getMaxDistance();
            boolean isWeightEligibleForOffer = packageForDelivery.getWeightInKgs() >= discount.getMinWeight() && packageForDelivery.getWeightInKgs() <= discount.getMaxWeight();
            return isDistanceEligibleForOffer && isWeightEligibleForOffer;
        } else return false;
    }

    public int getDiscountPercentForPackage(Package packageForDelivery) {
        if (isPackageEligibleForDiscount(packageForDelivery, packageForDelivery.getOfferCode())) {
            return discountRepository.findByOfferCode(packageForDelivery.getOfferCode()).get().getOfferedPercentage();
        } else {
            return 0;
        }
    }

    public int calculateDiscountPriceForPackage(int totalCost, int discountPercent) {
        return (discountPercent * totalCost) / 100;
    }
}
