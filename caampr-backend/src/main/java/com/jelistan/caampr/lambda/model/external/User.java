package com.jelistan.caampr.lambda.model.external;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User {
    private final String userId;
    private final String firstName;
    private final String lastName;
}
