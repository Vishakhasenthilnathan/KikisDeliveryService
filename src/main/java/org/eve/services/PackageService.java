package org.eve.services;

import org.eve.entities.OfferCode;
import org.eve.entities.Package;
import org.eve.repositories.interfaces.IPackageRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PackageService {
    private final IPackageRepository packageRepository;

    public PackageService(IPackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    public void createPackage(String id, int weight, int distance, OfferCode offerCode) {
        packageRepository.save(new Package(id, weight, distance, offerCode));
    }

    public HashMap<String, Package> getAllPackages() {
        return packageRepository.findAll();
    }

    public List<Package> getPackageByPackageIds(List<String> packageIds) {
        List<Package> packageList = new ArrayList<>();
        for (String _package : packageIds) {
            packageList.add(packageRepository.findById(_package));
        }
        return packageList;
    }
}

