package org.eve.services;

import org.eve.entities.DeliveryVehicle;
import org.eve.entities.DeliveryVehicles;
import org.eve.repositories.interfaces.IDeliveryVehiclesRepository;

import java.util.Comparator;
import java.util.List;

public class DeliveryVehiclesService {
    private final IDeliveryVehiclesRepository deliveryVehiclesRepository;

    public DeliveryVehiclesService(IDeliveryVehiclesRepository deliveryVehiclesRepository) {
        this.deliveryVehiclesRepository = deliveryVehiclesRepository;
    }

    public void registerDeliveryVehiclesInfo(int numberOfVehicles, int maxCapacityLoad, int maxSpeed) {
        deliveryVehiclesRepository.save(numberOfVehicles, maxCapacityLoad, maxSpeed);
    }

    public DeliveryVehicles getDeliveryVehicleInfo() {
        return deliveryVehiclesRepository.getDeliveryVehicles();
    }

    public DeliveryVehicle getVehicleCompletingDeliveryFirst() {
        List<DeliveryVehicle> deliveryVehicleList = deliveryVehiclesRepository.getDeliveryVehicles().getDeliveryVehicleList();
        return deliveryVehicleList.stream().min(Comparator.comparingDouble(DeliveryVehicle::getTimeAtWhichDeliveryCompletes)).get();
    }

    public void updateDeliveryVehicleAvailability(int deliveryVehicleId, boolean isAvailable, List<String> packagesInThatDeliveryVehicle, double timeAtWhichDeliveryCompletes) {
        DeliveryVehicle deliveryVehicle = deliveryVehiclesRepository.getDeliveryVehicles().getDeliveryVehicleList().stream().filter(x -> x.getId() == deliveryVehicleId).findFirst().get();
        deliveryVehicle.setAvailable(isAvailable);
        deliveryVehicle.setTimeAtWhichDeliveryCompletes(timeAtWhichDeliveryCompletes);
        deliveryVehicle.setPackageList(packagesInThatDeliveryVehicle);
    }
}
