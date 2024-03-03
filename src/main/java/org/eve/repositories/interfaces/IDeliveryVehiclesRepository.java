package org.eve.repositories.interfaces;

import org.eve.entities.DeliveryVehicle;
import org.eve.entities.DeliveryVehicles;

import java.util.List;
import java.util.Optional;

public interface IDeliveryVehiclesRepository {

    DeliveryVehicles save(int numberOfVehicles, int maxLoadCapacity ,int maxSpeed, int numberOfPackages);

    Optional<DeliveryVehicle> findVehicleByPackageId(String packageId);

    List<DeliveryVehicle> findAllDeliveryVehicles();

    void deleteByPackageId(String packageId);

    void updateDeliveryVehicle(DeliveryVehicle deliveryVehicle);

    DeliveryVehicles getDeliveryVehicles();

}
