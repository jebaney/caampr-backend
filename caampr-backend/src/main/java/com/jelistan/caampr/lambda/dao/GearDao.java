package com.jelistan.caampr.lambda.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jelistan.caampr.lambda.model.internal.DynamoGear;
import com.jelistan.caampr.lambda.model.internal.GearRequest;
import com.jelistan.caampr.lambda.model.internal.VisibilityTypes;

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
     * Fetches a JSON object of a piece of gear from the Dynamo server based on the gear's associated gear ID, and
     * deserializes it into a DynamoGear object.
     * @param request The GearRequest object containing the required info (gearId, etc)
     * @return The List of DynamoGear objects with matching gear IDs, which may be an empty list.
     */
    public List<DynamoGear> getGear(final GearRequest request){
        DynamoGear partitionKey = DynamoGear.builder()
                .gearId(request.getGearId())
                .build();

        DynamoDBQueryExpression<DynamoGear> queryExpression = new DynamoDBQueryExpression<DynamoGear>()
                .withHashKeyValues(partitionKey)
                .withRangeKeyCondition(DynamoGear.TYPE_FIELD,
                        new Condition()
                                .withComparisonOperator(ComparisonOperator.EQ)
                                .withAttributeValueList(
                                        Lists.newArrayList(
                                                new AttributeValue().withS(request.getType().name())
                                        )
                                )
                )
                //.withIndexName(DynamoGear.GEAR_BY_PROFILE_INDEX)
                .withConsistentRead(false);

        final Map<String, Condition> filters = Maps.newHashMap();
        addVisibilityFilter(filters, request.getVisibility());

        queryExpression.withQueryFilter(filters);

        return mapper.query(DynamoGear.class, queryExpression);
    }

    private void addVisibilityFilter(final Map<String, Condition> filters,
                                     final VisibilityTypes visibilityType) {
        // If the type is private, then return all
        // Probably need some checking for the private case where the
        // caller is the owner
        if (visibilityType == VisibilityTypes.PUBLIC) {
            filters.put(DynamoGear.VISIBILITY_FIELD,
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
