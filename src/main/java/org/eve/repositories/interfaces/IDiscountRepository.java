package org.eve.repositories.interfaces;

import org.eve.entities.Discount;
import org.eve.entities.OfferCode;

import java.util.*;

public interface IDiscountRepository {
    Discount save(Discount discount);
    Optional<Discount> findByOfferCode(OfferCode offerCode);
    List<Discount> findAll();
    void deleteByOfferCode(OfferCode offerCode);
    long count();
}
