package com.jelistan.caampr.lambda.model;

public enum VisibilityTypes {
    PRIVATE,
    PUBLIC;

    VisibilityTypes() {
    }

    public static VisibilityTypes fromString(final String input) {
        final String inputUpper = input.toUpperCase().trim();
        for (VisibilityTypes type : VisibilityTypes.values()) {
            if (type.name().equals(inputUpper)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid/Unknown visibility type [" + input + "]");
    }
}
