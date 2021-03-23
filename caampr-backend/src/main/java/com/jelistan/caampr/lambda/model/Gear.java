package com.jelistan.caampr.lambda.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@DynamoDBTable(tableName = "Gear-v2b")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gear {

    public static final String GEAR_BY_PROFILE_INDEX = "gear-by-profile";
    public static final String PROFILE_ID_FIELD = "profileId";
    public static final String TYPE_FIELD = "type";
    public static final String VISIBILITY_FIELD = "visibility";

    @DynamoDBHashKey
    private String gearId;

    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
    @DynamoDBRangeKey
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = GEAR_BY_PROFILE_INDEX)
    private GearTypes type;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = GEAR_BY_PROFILE_INDEX)
    private String profileId;

    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.S)
    private VisibilityTypes visibility;
    private String name;
    private String title;
    private String description;
    private Link link;
    private String imageUrl;

}
