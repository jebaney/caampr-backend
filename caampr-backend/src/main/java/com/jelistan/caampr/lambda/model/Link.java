package com.jelistan.caampr.lambda.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Link {
    private int id;
    private String url;
    private LinkTypes type;
}
