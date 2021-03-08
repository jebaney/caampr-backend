package com.jelistan.caampr.lambda.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Link {
    private final int id;
    private final String url;
    private final LinkTypes type;
}
