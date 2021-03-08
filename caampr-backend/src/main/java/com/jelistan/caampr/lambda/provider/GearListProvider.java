package com.jelistan.caampr.lambda.provider;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.jelistan.caampr.lambda.model.Link;
import com.jelistan.caampr.lambda.model.LinkTypes;
import com.jelistan.caampr.lambda.provider.GearProvider;
import com.google.common.collect.Lists;
import com.jelistan.caampr.lambda.model.Gear;
import java.util.List;

public class GearListProvider implements GearProvider{

    public List<Gear> getGearList(final int profileId){
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();

        DynamoDBMapper mapper = new DynamoDBMapper(client);

        Gear partitionKey = Gear.builder()
                .id(profileId)
                .build();

        DynamoDBQueryExpression<Gear> queryExpression = new DynamoDBQueryExpression<Gear>()
            .withHashKeyValues(partitionKey);

        List<Gear> itemList = mapper.query(Gear.class, queryExpression);

        return itemList;
        
    }
}