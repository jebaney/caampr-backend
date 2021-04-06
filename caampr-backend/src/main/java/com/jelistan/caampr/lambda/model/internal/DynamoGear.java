package com.jelistan.caampr.lambda.model.internal;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@DynamoDBTable(tableName = "Gear-v3")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DynamoGear {

    public static final String GEAR_BY_PROFILE_INDEX = "gear-by-profile";
    public static final String PROFILE_ID_FIELD = "userId";
    public static final String TYPE_FIELD = "type";
    public static final String VISIBILITY_FIELD = "visibility";

    @DynamoDBHashKey
    private String gearId;

    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
    @DynamoDBRangeKey
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = GEAR_BY_PROFILE_INDEX)
    private GearTypes type;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = GEAR_BY_PROFILE_INDEX)
    private String userId;

    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
    private VisibilityTypes visibility;
    private String name;
    private String description;
    private Link link;
    private String imageUrl;

}
