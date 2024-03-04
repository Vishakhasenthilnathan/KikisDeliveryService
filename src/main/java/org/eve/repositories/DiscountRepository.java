package org.eve.repositories;

import org.eve.entities.Discount;
import org.eve.entities.OfferCode;
import org.eve.repositories.interfaces.IDiscountRepository;

import java.util.HashMap;
import java.util.Optional;

public class DiscountRepository implements IDiscountRepository {

    HashMap<OfferCode, Discount> discountStore;

    public DiscountRepository() {
        discountStore = new HashMap<>();
        discountStore.put(OfferCode.OFR001, new Discount(OfferCode.OFR001, 0, 200, 70, 200, 10));
        discountStore.put(OfferCode.OFR002, new Discount(OfferCode.OFR002, 50, 150, 100, 250, 7));
        discountStore.put(OfferCode.OFR003, new Discount(OfferCode.OFR003, 50, 250, 10, 150, 5));
    }

    @Override
    public Discount save(Discount discount) {
        discountStore.put(discount.getOfferCode(), discount);
        return discount;
    }

    @Override
    public Optional<Discount> findByOfferCode(OfferCode offerCode) {
        return Optional.ofNullable(discountStore.get(offerCode));
    }
}
