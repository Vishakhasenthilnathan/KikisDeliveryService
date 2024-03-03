package org.eve.repositories.interfaces;

import org.eve.entities.Package;

import java.util.List;

public interface IPackageRepository {
    Package save(Package packageForDelivery);
    boolean existsById(String id);
    Package findById(String id);
    List<Package> findAll();
    void deleteById(String id);
    long count();
}
