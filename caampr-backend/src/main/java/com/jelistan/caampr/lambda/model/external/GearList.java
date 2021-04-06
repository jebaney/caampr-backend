package com.jelistan.caampr.lambda.model.external;


import com.jelistan.caampr.lambda.model.internal.VisibilityTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GearList {

    private String listId;

    private String userId;

    private VisibilityTypes visibility;
    private String name;
    private String description;
    private List<Gear> entries;
}
