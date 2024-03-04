package org.eve.repositories.interfaces;

import org.eve.entities.DeliveryVehicles;

public interface IDeliveryVehiclesRepository {
    DeliveryVehicles save(int numberOfVehicles, int maxLoadCapacity, int maxSpeed);

    DeliveryVehicles getDeliveryVehicles();

}
