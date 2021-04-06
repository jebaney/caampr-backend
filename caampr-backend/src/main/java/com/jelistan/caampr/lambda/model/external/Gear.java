package com.jelistan.caampr.lambda.model.external;

import com.jelistan.caampr.lambda.model.internal.GearTypes;
import com.jelistan.caampr.lambda.model.internal.Link;
import com.jelistan.caampr.lambda.model.internal.VisibilityTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Gear {

    private String gearId;

    private GearTypes type;

    private String userId;

    private VisibilityTypes visibility;
    private String name;
    private String description;
    private Link link;
    private String imageUrl;

}
