package org.eve.entities;

public class DeliveryInfo {
    private int id;
    private final int baseDeliveryCost;
    private final String packageId;
    private int totalDeliveryCost;
    private int discountPrice;
    private int totalDeliveryCostIncludingDiscountPrice;
    private double estimatedDeliveryTime;

    public double getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(double estimatedDeliveryTime) {
        double estimatedTime = Math.floor(estimatedDeliveryTime * 100.0) / 100.0;
        this.estimatedDeliveryTime = estimatedTime;
    }

    public DeliveryInfo(int baseDeliveryCost, String packageId) {
        this.baseDeliveryCost = baseDeliveryCost;
        this.packageId = packageId;
    }
    public DeliveryInfo(int baseDeliveryCost, String packageId, int totalDeliveryCost, int discountPrice, int totalDeliveryCostIncludingDiscountPrice) {
        this.baseDeliveryCost = baseDeliveryCost;
        this.packageId = packageId;
        this.totalDeliveryCost = totalDeliveryCost;
        this.discountPrice = discountPrice;
        this.totalDeliveryCostIncludingDiscountPrice = totalDeliveryCostIncludingDiscountPrice;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id =  id;
    }

    public int getBaseDeliveryCost() {
        return baseDeliveryCost;
    }

    public String getPackageId() {
        return packageId;
    }

    public double getTotalDeliveryCost() {
        return totalDeliveryCost;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public double getTotalDeliveryCostIncludingDiscountPrice() {
        return totalDeliveryCostIncludingDiscountPrice;
    }

    @Override
    public String toString() {
        String estimatedDeliveryTime = this.estimatedDeliveryTime==0.0 ? "": " "+ this.estimatedDeliveryTime;
        return this.packageId +" "+this.discountPrice +" "+ this.totalDeliveryCostIncludingDiscountPrice+ estimatedDeliveryTime;
    }
}
