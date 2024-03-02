package org.eve;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new RuntimeException();
        }
        List<String> commandLineArgs = new ArrayList<>(Arrays.asList(args));
        run(commandLineArgs);

    }

    public static void run(List<String> commandLineArgs) {
        System.out.println(commandLineArgs);
    }
}