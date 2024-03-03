package org.eve.entities;

import java.util.List;

public class DeliveryVehicle {

    private List<String> packageList;
    private int id;
    private boolean isAvailable;
    private double timeAtWhichDeliveryCompletes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DeliveryVehicle(int id, List<String> packageList, boolean isAvailable, double timeAtWhichDeliveryCompletes) {
        this.id = id;
        this.packageList = packageList;
        this.isAvailable = isAvailable;
        this.timeAtWhichDeliveryCompletes = timeAtWhichDeliveryCompletes;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public double getTimeAtWhichDeliveryCompletes() {
        return timeAtWhichDeliveryCompletes;
    }

    public void setTimeAtWhichDeliveryCompletes(double timeAtWhichDeliveryCompletes) {
        this.timeAtWhichDeliveryCompletes = timeAtWhichDeliveryCompletes;
    }

    public List<String> getPackageList() {
        return packageList;
    }

    public void setPackageList(List<String> packageList) {
        this.packageList = packageList;
    }
}
