package org.eve;

import org.eve.commands.CommandKeyword;
import org.eve.commands.CommandRegistry;
import org.eve.entities.OfferCode;
import org.eve.entities.Package;

import java.io.BufferedReader;
import java.io.Console;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.eve.entities.OfferCode.parseOfferCode;

public class App {
    public static void main(String[] args) {
//        if (args.length != 1) {
//            throw new RuntimeException();
//        }
//        List<String> commandLineArgs = new ArrayList<>(Arrays.asList(args));
//        run(commandLineArgs);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Kiki's Delivery Service! Please provide what information u need:");
        Configuration conf = Configuration.getInstance();

//        CommandRegistry commandRegistry = conf.getCommandRegistry();
        // Prompt for base_delivery_cost no_of_packges
        double baseDeliveryCost = scanner.nextDouble();
        int noOfPackages = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        List< Package> packageList = new ArrayList<>();
        while(noOfPackages>0){
            String packageInfo = scanner.nextLine();
            String[] packageIn = packageInfo.split(" ");
            String packageId = packageIn[0];
            int weight = Integer.parseInt(packageIn[1]);
            int distance = Integer.parseInt(packageIn[2]);
            OfferCode offerCode = parseOfferCode(packageIn[3]);
            packageList.add(new Package(packageId,weight,distance,offerCode));
            noOfPackages--;
        }
//        // Print user information
        packageList.forEach(System.out::println);

        scanner.close();
    }

    public static void run(List<String> commandLineArgs) {
        Configuration conf = Configuration.getInstance();

        CommandRegistry commandRegistry = conf.getCommandRegistry();

        String inputFile = commandLineArgs.get(0).split("=")[1];

        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                commandRegistry.invokeCommand(reader, line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}