package org.eve.repositories.interfaces;

import org.eve.entities.DeliveryInfo;

import java.util.List;

public interface IDeliveryInfoRepository {
    DeliveryInfo save(DeliveryInfo deliveryInfo);

    void updateDeliveryInfoWithEstimatedTime(String packageId, double estimatedDeliveryTime);

    List<DeliveryInfo> getAll();
}
