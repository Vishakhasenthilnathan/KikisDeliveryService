package org.eve.entities;

public enum OfferCode {
    OFR001("OFR001"), OFR002("OFR002"), OFR003("OFR003");

    private String name;

    OfferCode(String name) {
        this.name = name;
    }

    public String getOfferCode() {
        return name;
    }


    // Method to convert string input to enum value
    public static OfferCode parseOfferCode(String input) {
        for (OfferCode code : OfferCode.values()) {
            if (code.name().equals(input.toUpperCase())) {
                return code;
            }
        }
        return null;
    }

}
