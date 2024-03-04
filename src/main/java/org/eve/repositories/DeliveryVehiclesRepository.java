package org.eve.repositories;

import org.eve.entities.DeliveryVehicle;
import org.eve.entities.DeliveryVehicles;
import org.eve.repositories.interfaces.IDeliveryVehiclesRepository;

import java.util.ArrayList;
import java.util.List;

public class DeliveryVehiclesRepository implements IDeliveryVehiclesRepository {
    private DeliveryVehicles deliveryVehicles;

    @Override
    public DeliveryVehicles save(int numberOfVehicles, int maxLoadCapacity, int maxSpeed) {
        deliveryVehicles = new DeliveryVehicles(numberOfVehicles, maxLoadCapacity, maxSpeed);
        List<DeliveryVehicle> deliveryVehicleList = new ArrayList<>();
        int id = 1;
        while (numberOfVehicles > 0) {
            DeliveryVehicle deliveryVehicle = new DeliveryVehicle(id++, new ArrayList<>(), true, 0);
            deliveryVehicleList.add(deliveryVehicle);
            numberOfVehicles--;
        }
        deliveryVehicles.setDeliveryVehicleList(deliveryVehicleList);

        return deliveryVehicles;
    }

    @Override
    public DeliveryVehicles getDeliveryVehicles() {
        return deliveryVehicles;
    }
}
