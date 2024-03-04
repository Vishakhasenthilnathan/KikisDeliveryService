package org.eve.repositories;

import org.eve.entities.Package;
import org.eve.repositories.interfaces.IPackageRepository;

import java.util.HashMap;

public class PackageRepository implements IPackageRepository {
    HashMap<String, Package> packageStore;

    public PackageRepository() {
        this.packageStore = new HashMap<>();
    }

    @Override
    public Package save(Package packageForDelivery) {
        packageStore.put(packageForDelivery.getId(), packageForDelivery);
        return packageForDelivery;
    }

    @Override
    public Package findById(String id) {
        return packageStore.get(id);
    }

    @Override
    public HashMap<String, Package> findAll() {
        return packageStore;
    }
}
