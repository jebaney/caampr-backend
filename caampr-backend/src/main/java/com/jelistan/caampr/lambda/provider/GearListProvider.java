package com.jelistan.caampr.lambda.provider;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.jelistan.caampr.lambda.model.Gear;
import com.jelistan.caampr.lambda.model.GearListRequest;

import java.util.List;

public class GearListProvider implements GearProvider{

    public List<Gear> getGearList(final GearListRequest request){
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();

        DynamoDBMapper mapper = new DynamoDBMapper(client);

        Gear partitionKey = Gear.builder()
                .id(request.getProfileId())
                .build();

        DynamoDBQueryExpression<Gear> queryExpression = new DynamoDBQueryExpression<Gear>()
            .withHashKeyValues(partitionKey);

        List<Gear> itemList = mapper.query(Gear.class, queryExpression);

        return itemList;
        
    }
}