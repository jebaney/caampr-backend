package com.jelistan.caampr.lambda.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@DynamoDBTable(tableName = "Gear-v1")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gear {

    @DynamoDBHashKey
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "gear-by-profile")
    private String id;
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "gear-by-profile")
    private String profileId;
    private String name;
    private String title;
    private String description;
    private Link link;
    private String imageUrl;

}
