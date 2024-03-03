package org.eve.repositories;

import org.eve.entities.Discount;
import org.eve.entities.OfferCode;
import org.eve.repositories.interfaces.IDiscountRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DiscountRepository implements IDiscountRepository {

    HashMap<OfferCode,Discount> discountsMap;

    public DiscountRepository(){
        discountsMap = new HashMap<>();
        discountsMap.put(OfferCode.OFR001, new Discount(OfferCode.OFR001,0,200,70,200,10));
        discountsMap.put(OfferCode.OFR002, new Discount(OfferCode.OFR002,50,150,100,250,7));
        discountsMap.put(OfferCode.OFR003, new Discount(OfferCode.OFR003,50,250,10,150,5));
    }
    @Override
    public Discount save(Discount discount) {
        discountsMap.put(discount.getOfferCode(),discount);
        return discount;
    }
    @Override
    public Optional<Discount> findByOfferCode(OfferCode offerCode) {
        return Optional.ofNullable(discountsMap.get(offerCode));
    }
    @Override
    public List<Discount> findAll() {
        return new ArrayList<>(discountsMap.values());
    }

    @Override
    public void deleteByOfferCode(OfferCode offerCode) {
        discountsMap.remove(offerCode);
    }

    @Override
    public long count() {
        return discountsMap.size();
    }
}
