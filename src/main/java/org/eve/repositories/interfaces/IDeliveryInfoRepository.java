package org.eve.repositories.interfaces;

import org.eve.entities.DeliveryInfo;

import java.util.List;
import java.util.Optional;

public interface IDeliveryInfoRepository {
    DeliveryInfo save(DeliveryInfo deliveryInfo);
    Optional<DeliveryInfo> findByPackageId(String packageId);
    void updateDeliveryInfoWithEstimatedTime(String packageId, double estimatedDeliveryTime);
    List<DeliveryInfo> findAll();
    void deleteByPackageId(String packageId);
    long count();
}
