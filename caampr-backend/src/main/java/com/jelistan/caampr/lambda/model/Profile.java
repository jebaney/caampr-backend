package com.jelistan.caampr.lambda.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Builder;
import lombok.Data;

@DynamoDBTable(tableName = "Gear-v1")
@Builder
@Data
public class Profile {
    @DynamoDBHashKey(attributeName = "id")
    private final int id;
    private final String firstName;
    private final String lastName;
    @DynamoDBAttribute(attributeName = "profileId")
    private final String profileName;

}
