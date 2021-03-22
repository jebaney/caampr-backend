package com.jelistan.caampr.lambda.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Link {
    private int id;
    private String url;
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
    private LinkTypes type;
}
