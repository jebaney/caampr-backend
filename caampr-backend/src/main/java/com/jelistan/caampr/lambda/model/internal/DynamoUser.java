package com.jelistan.caampr.lambda.model.internal;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Builder;
import lombok.Data;

@DynamoDBTable(tableName = "Users-v3")
@Builder
@Data
public class DynamoUser {
    @DynamoDBHashKey(attributeName = "userId")
    private final String userId;
    private final String firstName;
    private final String lastName;
}
