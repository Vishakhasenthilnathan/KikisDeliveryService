package org.eve;

import org.eve.commands.CalculateDeliveryCostCommand;
import org.eve.commands.CalculateDeliveryTimeCommand;
import org.eve.commands.CommandKeyword;
import org.eve.commands.CommandRegistry;
import org.eve.repositories.DeliveryInfoRepository;
import org.eve.repositories.DeliveryVehiclesRepository;
import org.eve.repositories.DiscountRepository;
import org.eve.repositories.PackageRepository;
import org.eve.repositories.interfaces.IDeliveryInfoRepository;
import org.eve.repositories.interfaces.IDeliveryVehiclesRepository;
import org.eve.repositories.interfaces.IDiscountRepository;
import org.eve.repositories.interfaces.IPackageRepository;
import org.eve.services.DeliveryInfoService;
import org.eve.services.DeliveryVehiclesService;
import org.eve.services.DiscountService;
import org.eve.services.PackageService;

public class Configuration {

    private static final Configuration instance = new Configuration();

    private Configuration() {
    }

    public static Configuration getInstance() {
        return instance;
    }

    private final IPackageRepository packageRepository = new PackageRepository();
    private final IDiscountRepository discountRepository = new DiscountRepository();
    private final IDeliveryInfoRepository deliveryInfoRepository = new DeliveryInfoRepository();
    private final IDeliveryVehiclesRepository deliveryVehiclesRepository = new DeliveryVehiclesRepository();
    private final PackageService packageService = new PackageService(packageRepository);
    private final DiscountService discountService = new DiscountService(discountRepository);
    private final DeliveryVehiclesService deliveryVehiclesService = new DeliveryVehiclesService(packageService,deliveryVehiclesRepository);
    private final DeliveryInfoService deliveryInfoService = new DeliveryInfoService(packageService, discountService,deliveryVehiclesService, deliveryInfoRepository);
    private final CalculateDeliveryCostCommand calculateDeliveryCostCommand = new CalculateDeliveryCostCommand(deliveryInfoService, packageService);
    private final CalculateDeliveryTimeCommand calculateDeliveryTimeCommand = new CalculateDeliveryTimeCommand(deliveryInfoService,packageService, deliveryVehiclesService);
    private final CommandRegistry commandRegistry = new CommandRegistry();

    private void registerCommands() {
        commandRegistry.registerCommand(CommandKeyword.GET_DELIVERY_COST_COMMAND.getName(), calculateDeliveryCostCommand);
        commandRegistry.registerCommand(CommandKeyword.GET_DELIVERY_TIME_COMMAND.getName(), calculateDeliveryTimeCommand);
    }

    public CommandRegistry getCommandRegistry() {
        registerCommands();
        return commandRegistry;
    }
}
