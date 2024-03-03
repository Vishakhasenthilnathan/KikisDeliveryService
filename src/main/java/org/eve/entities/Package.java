package org.eve.entities;

public class Package {
    private String id;
    private final int weightInKgs;
    private final int distanceInKms;
    private final OfferCode offerCode;

    public Package(String id,int weightInKgs, int distanceInKms,OfferCode offerCode){
        this.id = id;
        this.weightInKgs = weightInKgs;
        this.distanceInKms = distanceInKms;
        this.offerCode = offerCode;
    }

    public String getId() {
        return id;
    }
    public int getWeightInKgs() {
        return weightInKgs;
    }
    public int getDistanceInKms() {
        return distanceInKms;
    }
    public OfferCode getOfferCode() {
        return offerCode;
    }
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return this.id;
    }
}
