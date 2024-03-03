package org.eve.entities;

import org.w3c.dom.ranges.Range;

public class Discount {
    private int id;
    private OfferCode offerCode;
    private final int minDistance;
    private final int maxDistance;
    private final int minWeight;
    private final int maxWeight;

    private final int offeredPercentage;

    public int getId() {
        return id;
    }

    public OfferCode getOfferCode() {
        return offerCode;
    }

    public int getMinDistance() {
        return minDistance;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public int getMinWeight() {
        return minWeight;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public int getOfferedPercentage() {
        return offeredPercentage;
    }
    public Discount(OfferCode offerCode, int minDistance, int maxDistance, int minWeight, int maxWeight, int offeredPercentage) {
        this.offerCode = offerCode;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.offeredPercentage = offeredPercentage;
    }
}
