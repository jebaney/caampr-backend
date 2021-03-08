package com.jelistan.caampr.lambda.adaptor;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.jelistan.caampr.lambda.model.GearListRequest;

/**
 * Converts APIGatewayProxyRequestEvent (external) to GearListRequest (internal)
 */

public class GearListRequestAdaptor{
    public GearListRequest convert(APIGatewayProxyRequestEvent requestEvent){
        String[] tokens = requestEvent.getPath().split("/");
        String profileId = tokens[2];
        GearListRequest request = GearListRequest.builder()
                .callerId("6969") //temporary hardcode until requester info is implemented
                .profileId(profileId)
                .build();
        return request;
    }
}
