package com.jelistan.caampr.lambda.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Builder;
import lombok.Data;

@DynamoDBTable(tableName = "Users-v1b")
@Builder
@Data
public class Profile {
    @DynamoDBHashKey(attributeName = "profileId")
    private final String profileId;
    private final String firstName;
    private final String lastName;
}
