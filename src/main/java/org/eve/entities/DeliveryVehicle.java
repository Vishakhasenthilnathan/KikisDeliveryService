package org.eve.entities;

import java.util.List;

public class DeliveryVehicle {

    private int id;
    private boolean isAvailable;
    private double timeAtWhichDeliveryCompletes;
    private List<String> packageList;

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
        double time = Math.floor(timeAtWhichDeliveryCompletes * 100.0) / 100.0;
        this.timeAtWhichDeliveryCompletes = time;
    }

    public int getId() {
        return id;
    }

    public void setPackageList(List<String> packageList) {
        this.packageList = packageList;
    }
}
