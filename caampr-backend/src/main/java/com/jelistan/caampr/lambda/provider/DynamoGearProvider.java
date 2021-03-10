package com.jelistan.caampr.lambda.provider;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.jelistan.caampr.lambda.model.Gear;

import javax.inject.Inject;
import java.util.List;

public class DynamoGearProvider implements GearProvider{

    private final DynamoDBMapper mapper;

    /**
     * Constructor for Gear Provider implementation, takes Dynamo mapper as input.
     * @param mapper
     */
    @Inject

    public DynamoGearProvider(DynamoDBMapper mapper){
        this.mapper = mapper;
    }

    /**
     * Fetches a JSON list of gear objects from the Dynamo server based on the gears' associated profile ID, and
     * deserializes it into a List<Gear>.
     * @param profileId The user ID with which to search/filter
     * @return The List of Gear objects with matching profile IDs, which may be an empty list.
     */
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

    /**
     * Fetches a JSON list which will contain either a single Gear object with an ID matching gearId, or an empty list
     * if no match is found from the Dynamo database.
     * @param gearId The gear's ID with which to search/filter
     * @return Either a list containing a single Gear object, or an empty list if no match is found.
     */
    public List<Gear> getGearItem(final String gearId){
        Gear partitionKey = Gear.builder()
                .profileId(gearId)
                .build();

        DynamoDBQueryExpression<Gear> queryExpression = new DynamoDBQueryExpression<Gear>()
                .withHashKeyValues(partitionKey)
                .withConsistentRead(false);

        List<Gear> itemList = mapper.query(Gear.class, queryExpression);

        return itemList;
    }


}