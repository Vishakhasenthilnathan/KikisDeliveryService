package org.eve.entities;

import java.util.List;

public class DeliveryVehicles {
    private int numberOfVehicles;

    public List<DeliveryVehicle> getDeliveryVehicleList() {
        return deliveryVehicleList;
    }

    public void setDeliveryVehicleList(List<DeliveryVehicle> deliveryVehicleList) {
        this.deliveryVehicleList = deliveryVehicleList;
    }

    public int getNumberOfPendingPackages() {
        return numberOfPendingPackages;
    }

    public void setNumberOfPendingPackages(int numberOfPendingPackages) {
        this.numberOfPendingPackages = numberOfPendingPackages;
    }

    public int getNumberOfAvailableVehicles() {
        return numberOfAvailableVehicles;
    }

    public void setNumberOfAvailableVehicles(int numberOfAvailableVehicles) {
        this.numberOfAvailableVehicles = numberOfAvailableVehicles;
    }

    public void setMaxLoadCapacity(int maxLoadCapacity) {
        this.maxLoadCapacity = maxLoadCapacity;
    }

    public void setAvailableLoadCapacity(int availableLoadCapacity) {
        this.availableLoadCapacity = availableLoadCapacity;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    private List<DeliveryVehicle> deliveryVehicleList;
    private int numberOfPendingPackages;
    private int numberOfAvailableVehicles;
    private int maxLoadCapacity;
    private int availableLoadCapacity;
    private int maxSpeed;

    private double currentTime=0;

    public double getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(double currentTime) {
        this.currentTime = currentTime;
    }

    public int getAvailableLoadCapacity() {
        return availableLoadCapacity;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public int getMaxLoadCapacity() {
        return maxLoadCapacity;
    }

    public DeliveryVehicles(int numberOfVehicles, int numberOfPendingPackages, int numberOfAvailableVehicles, int maxLoadCapacity, int availableLoadCapacity, int maxSpeed) {
        this.numberOfVehicles = numberOfVehicles;
        this.numberOfPendingPackages = numberOfPendingPackages;
        this.numberOfAvailableVehicles = numberOfAvailableVehicles;
        this.maxLoadCapacity = maxLoadCapacity;
        this.availableLoadCapacity = availableLoadCapacity;
        this.maxSpeed = maxSpeed;
    }

    public DeliveryVehicles(int numberOfVehicles, int maxLoadCapacity, int maxSpeed) {
        this.numberOfVehicles = numberOfVehicles;
        this.maxLoadCapacity = maxLoadCapacity;
        this.maxSpeed = maxSpeed;
    }

    public int getNumberOfVehicles() {
        return numberOfVehicles;
    }

    public void setNumberOfVehicles(int numberOfVehicles) {
        this.numberOfVehicles = numberOfVehicles;
    }

    @Override
    public String toString() {
        return "DeliveryVehicles{" +
                "numberOfVehicles=" + numberOfVehicles +
                ", numberOfPendingPackages=" + numberOfPendingPackages +
                ", numberOfAvailableVehicles=" + numberOfAvailableVehicles +
                ", maxLoadCapacity=" + maxLoadCapacity +
                ", availableLoadCapacity=" + availableLoadCapacity +
                ", maxSpeed=" + maxSpeed +
                '}';
    }
}
