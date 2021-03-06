package com.jelistan.caampr.lambda.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request implementation that holds information about the profile being called and the profile of the caller.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GearListRequest {
    private String callerId;
    private String profileId;
    private GearTypes type;
    private VisibilityTypes visibility;
}
