package org.eve.repositories.interfaces;

import org.eve.entities.Package;

import java.util.HashMap;

public interface IPackageRepository {
    Package save(Package packageForDelivery);

    Package findById(String id);

    HashMap<String, Package> findAll();
}
