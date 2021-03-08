package com.jelistan.caampr.lambda.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Builder;
import lombok.Data;

@DynamoDBTable(tableName = "asdf")
@Builder
@Data
public class Gear {

    private final int id;
    private final String name;
    private final String title;
    private final String description;
    private final Link link;
    private final String imageUrl;

}
