package org.eve.repositories;

import org.eve.entities.DeliveryVehicle;
import org.eve.entities.DeliveryVehicles;
import org.eve.repositories.interfaces.IDeliveryVehiclesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeliveryVehiclesRepository implements IDeliveryVehiclesRepository {

    private DeliveryVehicles deliveryVehicles;

    @Override
    public DeliveryVehicles save(int numberOfVehicles, int maxLoadCapacity, int maxSpeed, int numberOfPackages) {
        deliveryVehicles = new DeliveryVehicles(numberOfVehicles, numberOfPackages, numberOfVehicles, maxLoadCapacity, maxLoadCapacity, maxSpeed);
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
    public Optional<DeliveryVehicle> findVehicleByPackageId(String packageId) {
        var deliveryVehicle = Optional.of((DeliveryVehicle) deliveryVehicles.getDeliveryVehicleList().stream().filter(x -> x.getPackageList().contains(packageId)));
        return deliveryVehicle;
    }

    @Override
    public List<DeliveryVehicle> findAllDeliveryVehicles() {
        return deliveryVehicles.getDeliveryVehicleList();
    }

    @Override
    public DeliveryVehicles getDeliveryVehicles() {
        return deliveryVehicles;
    }

    @Override
    public void deleteByPackageId(String packageId) {
        Optional<DeliveryVehicle> deliveryVehicle = findVehicleByPackageId(packageId);
        deliveryVehicle.ifPresent(x -> x.getPackageList().remove(packageId));
    }

    @Override
    public void updateDeliveryVehicle(DeliveryVehicle deliveryVehicle) {

    }
}
