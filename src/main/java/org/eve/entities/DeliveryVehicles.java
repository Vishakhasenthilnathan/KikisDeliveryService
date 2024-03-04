package org.eve.entities;

import java.util.List;

public class DeliveryVehicles {
    private final int numberOfVehicles;
    private List<DeliveryVehicle> deliveryVehicleList;
    private final int maxLoadCapacity;
    private final int maxSpeed;
    private double currentTime = 0;

    public double getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(double currentTime) {
        double currTime = Math.floor(currentTime * 100.0) / 100.0;
        this.currentTime = currTime;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public int getMaxLoadCapacity() {
        return maxLoadCapacity;
    }

    public List<DeliveryVehicle> getDeliveryVehicleList() {
        return deliveryVehicleList;
    }

    public void setDeliveryVehicleList(List<DeliveryVehicle> deliveryVehicleList) {
        this.deliveryVehicleList = deliveryVehicleList;
    }

    public int getNumberOfVehicles() {
        return numberOfVehicles;
    }

    public DeliveryVehicles(int numberOfVehicles, int maxLoadCapacity, int maxSpeed) {
        this.numberOfVehicles = numberOfVehicles;
        this.maxLoadCapacity = maxLoadCapacity;
        this.maxSpeed = maxSpeed;
    }
}
