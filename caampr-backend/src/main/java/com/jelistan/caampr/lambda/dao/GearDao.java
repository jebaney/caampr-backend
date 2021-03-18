package com.jelistan.caampr.lambda.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jelistan.caampr.lambda.model.Gear;
import com.jelistan.caampr.lambda.model.GearListRequest;
import com.jelistan.caampr.lambda.model.VisibilityTypes;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object for the Gear table
 */
public class GearDao {

    private final DynamoDBMapper mapper;

    /**
     * Constructor for Gear Provider implementation, takes Dynamo mapper as input.
     * @param mapper
     */
    @Inject
    public GearDao(final DynamoDBMapper mapper){
        this.mapper = mapper;
    }

    /**
     * Fetches a JSON list of gear objects from the Dynamo server based on the gears' associated profile ID, and
     * deserializes it into a List<Gear>.
     * @param request The GearListRequest object containing the required info (profileId, etc)
     * @return The List of Gear objects with matching profile IDs, which may be an empty list.
     */
    public List<Gear> getList(final GearListRequest request){
        Gear partitionKey = Gear.builder()
                .profileId(request.getProfileId())
                .build();

        DynamoDBQueryExpression<Gear> queryExpression = new DynamoDBQueryExpression<Gear>()
                .withHashKeyValues(partitionKey)
                .withRangeKeyCondition(Gear.TYPE_FIELD,
                        new Condition()
                                .withComparisonOperator(ComparisonOperator.EQ)
                                .withAttributeValueList(
                                        Lists.newArrayList(
                                                new AttributeValue().withS(request.getType().name())
                                        )
                                )
                )
                .withIndexName(Gear.GEAR_BY_PROFILE_INDEX)
                .withConsistentRead(false);

        final Map<String, Condition> filters = Maps.newHashMap();
        addVisibilityFilter(filters, request.getVisibility());

        queryExpression.withQueryFilter(filters);

        return mapper.query(Gear.class, queryExpression);
    }

    private void addVisibilityFilter(final Map<String, Condition> filters,
                                     final VisibilityTypes visibilityType) {
        // If the type is private, then return all
        // Probably need some checking for the private case where the
        // caller is the owner
        if (visibilityType == VisibilityTypes.PUBLIC) {
            filters.put(Gear.VISIBILITY_FIELD,
                    new Condition()
                            .withComparisonOperator(ComparisonOperator.EQ)
                            .withAttributeValueList(
                                    Lists.newArrayList(
                                            new AttributeValue().withS(VisibilityTypes.PUBLIC.name())
                                    )
                            )
            );
        }
    }

}
