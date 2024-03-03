package org.eve.repositories;

import org.eve.entities.DeliveryInfo;
import org.eve.repositories.interfaces.IDeliveryInfoRepository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DeliveryInfoRepository implements IDeliveryInfoRepository {

    HashMap<String,DeliveryInfo> deliveryInfoMap;
    private int autoIncrement;

    public DeliveryInfoRepository() {
        this.deliveryInfoMap = new HashMap<>();
    }

    @Override
    public DeliveryInfo save(DeliveryInfo deliveryInfo) {
        deliveryInfo.setId(autoIncrement++);
        deliveryInfoMap.put(deliveryInfo.getPackageId(),deliveryInfo);
        return deliveryInfo;
    }

    @Override
    public Optional<DeliveryInfo> findByPackageId(String packageId) {
        return Optional.ofNullable(deliveryInfoMap.get(packageId));
    }

    @Override
    public void updateDeliveryInfoWithEstimatedTime(String packageId, double estimatedDeliveryTime) {
       DeliveryInfo deliveryInfo = deliveryInfoMap.get(packageId);
       deliveryInfo.setEstimatedDeliveryTime(Math.floor(estimatedDeliveryTime * 100.0) / 100.0);
       deliveryInfoMap.put(packageId,deliveryInfo);
    }

    @Override
    public List<DeliveryInfo> findAll() {
        return deliveryInfoMap.values().stream().sorted(Comparator.comparing(DeliveryInfo::getPackageId)).collect(Collectors.toList());
    }

    @Override
    public void deleteByPackageId(String packageId) {
        deliveryInfoMap.remove(packageId);
    }

    @Override
    public long count() {
        return deliveryInfoMap.size();
    }
}
