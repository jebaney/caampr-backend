package com.jelistan.caampr.lambda.model.internal;


import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@DynamoDBTable(tableName = "Lists-v3")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DynamoGearList {
    public static final String LIST_BY_PROFILE_INDEX = "list-by-profile";
    public static final String PROFILE_ID_FIELD = "userId";
    public static final String TYPE_FIELD = "type";
    public static final String VISIBILITY_FIELD = "visibility";

    @DynamoDBHashKey
    private String listId;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = LIST_BY_PROFILE_INDEX)
    private String userId;

    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
    private VisibilityTypes visibility;
    private String name;
    private String description;
    private List<String> entries;
}
