package org.eve.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class CommandRegistry {
    private static final Map<String, ICommand> commands = new HashMap<>();
    public void registerCommand(String commandKeyword, ICommand command) {
        commands.putIfAbsent(commandKeyword, command);
    }
    private ICommand get(String commandName) {
        return commands.get(commandName);
    }

    public void invokeCommand(BufferedReader reader, String line) throws IOException {
        if(line.equals(CommandKeyword.GET_DELIVERY_COST_COMMAND.getName())){
            ICommand command = get(CommandKeyword.GET_DELIVERY_COST_COMMAND.getName());
            command.invoke(getCostCommandInput(reader));
        }
        else if(line.equals(CommandKeyword.GET_DELIVERY_TIME_COMMAND.getName())){
            ICommand command = get(CommandKeyword.GET_DELIVERY_TIME_COMMAND.getName());
            command.invoke(getTimeCommandInput(reader));
        }
        else{
            throw new RuntimeException("INVALID COMMAND 🛑");
        }
    }

    private List<String> getTimeCommandInput(BufferedReader reader) throws IOException {
        List<String> tokens = new ArrayList<>();
        tokens.addAll(getCostCommandInput(reader));
        String[] deliveryVehicleInfo = reader.readLine().split(" ");
        tokens.addAll(Arrays.asList(deliveryVehicleInfo));
        return tokens;
    }

    private static List<String> getCostCommandInput(BufferedReader reader) throws IOException {
        List<String> tokens = new ArrayList<>();
        String[] deliveryInfo = reader.readLine().split(" ");
        tokens.addAll(Arrays.asList(deliveryInfo));
        int noOfPackages = Integer.parseInt(tokens.get(1));
        while(noOfPackages>0){
            String packageInfo = reader.readLine();
            String[] packageIn = packageInfo.split(" ");
            tokens.addAll(Arrays.asList(packageIn));
            noOfPackages--;
        }
        return tokens;
    }

}
