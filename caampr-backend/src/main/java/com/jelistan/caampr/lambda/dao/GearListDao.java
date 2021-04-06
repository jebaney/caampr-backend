package com.jelistan.caampr.lambda.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jelistan.caampr.lambda.model.internal.DynamoGearList;
import com.jelistan.caampr.lambda.model.internal.GearListRequest;
import com.jelistan.caampr.lambda.model.internal.VisibilityTypes;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object for the List table
 */
public class GearListDao {

    private final DynamoDBMapper mapper;

    /**
     * Constructor for List Provider implementation, takes Dynamo mapper as input.
     * @param mapper
     */
    @Inject
    public GearListDao(final DynamoDBMapper mapper){
        this.mapper = mapper;
    }

    /**
     * Fetches a JSON list of gear list objects from the Dynamo server based on the gears' associated list ID, and
     * deserializes it into a List<DynamoGearList>. Should only contain 1 entry if a match is found, or 0 if no match
     * is found.
     * @param request The GearListRequest object containing the required info (listId, etc)
     * @return The List of DynamoGearList objects with matching list IDs, which may be an empty list.
     */
    public List<DynamoGearList> getList(final GearListRequest request){

        DynamoGearList partitionKey = DynamoGearList.builder()
                .listId(request.getListId())
                .build();

        DynamoDBQueryExpression<DynamoGearList> queryExpression = new DynamoDBQueryExpression<DynamoGearList>()
                .withHashKeyValues(partitionKey)
                /*.withRangeKeyCondition(DynamoGearList.TYPE_FIELD,
                        new Condition()
                                .withComparisonOperator(ComparisonOperator.EQ)
                                .withAttributeValueList(
                                        Lists.newArrayList(
                                                new AttributeValue().withS(request.getType().name())
                                        )
                                )
                )*/
                .withConsistentRead(false);

        final Map<String, Condition> filters = Maps.newHashMap();
        addVisibilityFilter(filters, request.getVisibility());

        queryExpression.withQueryFilter(filters);

        return mapper.query(DynamoGearList.class, queryExpression);
    }

    private void addVisibilityFilter(final Map<String, Condition> filters,
                                     final VisibilityTypes visibilityType) {
        // If the type is private, then return all
        // Probably need some checking for the private case where the
        // caller is the owner
        if (visibilityType == VisibilityTypes.PUBLIC) {
            filters.put(DynamoGearList.VISIBILITY_FIELD,
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
