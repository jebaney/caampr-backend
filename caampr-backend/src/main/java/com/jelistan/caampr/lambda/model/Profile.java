package com.jelistan.caampr.lambda.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Profile {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String profileName;

}
