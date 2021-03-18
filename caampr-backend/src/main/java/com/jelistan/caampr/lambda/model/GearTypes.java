package com.jelistan.caampr.lambda.model;

public enum GearTypes {
    GEAR,
    VEHICLE;

    GearTypes() {
    }

    public static GearTypes fromString(final String input) {
        final String inputUpper = input.toUpperCase().trim();
        for (GearTypes type: GearTypes.values()) {
            if (type.name().equals(inputUpper)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid/Unknown input type [" + input + "]");
    }
}
