package com.jelistan.caampr.lambda.model.internal;


import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@DynamoDBTable(tableName = "Tags-v3")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @DynamoDBHashKey
    private String name;

    private Set<String> entries;
}
