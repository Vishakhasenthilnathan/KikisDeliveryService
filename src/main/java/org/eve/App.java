package org.eve;

import org.eve.commands.CommandRegistry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try{
            System.out.println("Welcome to Kiki's Delivery Service! Please provide what information u need:");

            List<String> tokens = new ArrayList<>();
            //baseDeliveryCost
            tokens.add(scanner.next());
            String numberOfPackages = scanner.next();
            tokens.add(numberOfPackages);
            scanner.nextLine();
            int noOfPackages = Integer.parseInt(numberOfPackages);
            while (noOfPackages > 0) {
                String packageInfo = scanner.nextLine();
                String[] packageIn = packageInfo.split(" ");
                String packageId = packageIn[0];
                tokens.add(packageId);
                String weight = packageIn[1];
                String distance = packageIn[2];
                String offerCode = packageIn[3];
                tokens.add(weight);
                tokens.add(distance);
                tokens.add(offerCode);
                noOfPackages--;
            }
            run(tokens);
        }
        catch (Exception e){
            throw new InputMismatchException(e.getMessage());
        }
        finally {
            scanner.close();
        }
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