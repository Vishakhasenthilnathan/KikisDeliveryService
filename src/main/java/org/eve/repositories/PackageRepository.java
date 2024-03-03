package org.eve.repositories;

import org.eve.entities.Package;
import org.eve.repositories.interfaces.IPackageRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PackageRepository implements IPackageRepository {
    HashMap<String, Package> packageMap;

    public PackageRepository() {
        this.packageMap = new HashMap<>();
    }

    @Override
    public Package save(Package packageForDelivery) {
        packageMap.put(packageForDelivery.getId(), packageForDelivery);
        return packageForDelivery;
    }

    @Override
    public boolean existsById(String id) {
        return packageMap.containsKey(id);
    }

    @Override
    public Package findById(String id) {
        return packageMap.get(id);
    }

    @Override
    public List<Package> findAll() {
        return new ArrayList<>(packageMap.values());
    }

    @Override
    public void deleteById(String id) {
        packageMap.remove(id);
    }

    @Override
    public long count() {
        return packageMap.size();
    }
}
