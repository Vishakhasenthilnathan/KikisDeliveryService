package org.eve.commands;

public enum CommandKeyword {
    GET_DELIVERY_COST_COMMAND("GET_DELIVERY_COST"),
    GET_DELIVERY_TIME_COMMAND("GET_DELIVERY_TIME");
    private final String name;

    CommandKeyword(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
