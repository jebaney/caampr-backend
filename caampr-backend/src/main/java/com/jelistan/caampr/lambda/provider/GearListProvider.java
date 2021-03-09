package com.jelistan.caampr.lambda.provider;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.jelistan.caampr.lambda.model.Gear;

import javax.inject.Inject;
import java.util.List;

public class GearListProvider implements GearProvider{

    private final DynamoDBMapper mapper;

    @Inject
    public GearListProvider(DynamoDBMapper mapper){
        this.mapper = mapper;
    }

    public List<Gear> getGearList(final String profileId){
        Gear partitionKey = Gear.builder()
                .profileId(profileId)
                .build();

        DynamoDBQueryExpression<Gear> queryExpression = new DynamoDBQueryExpression<Gear>()
            .withHashKeyValues(partitionKey)
            .withIndexName("gear-by-profile")
            .withConsistentRead(false);

        List<Gear> itemList = mapper.query(Gear.class, queryExpression);

        return itemList;
        
    }
}