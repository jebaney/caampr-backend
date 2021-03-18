package com.jelistan.caampr.lambda.adaptor;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.jelistan.caampr.lambda.model.Gear;
import com.jelistan.caampr.lambda.model.GearListRequest;
import com.jelistan.caampr.lambda.model.GearTypes;
import com.jelistan.caampr.lambda.model.VisibilityTypes;

/**
 * Converts APIGatewayProxyRequestEvent (external) to GearListRequest (internal)
 * path: /user/[profileId]/gear
 */
public class GearListRequestAdaptor{

    /**
     * Adapt the incoming event object to an internal request, parsing data
     * from the path and other event attributes
     *
     * @param requestEvent  The event to adapt
     * @return              The internal model
     */
    public GearListRequest convert(APIGatewayProxyRequestEvent requestEvent) {
        String[] tokens = requestEvent.getPath().split("/");
        String profileId = tokens[2];
        String type = tokens[3];
        GearListRequest request = GearListRequest.builder()
                .callerId("6969") //TODO temporary hardcode until requester info is implemented, see backend issue #18
                .profileId(profileId)
                .type(GearTypes.fromString(type))
                .visibility(extractVisibility(requestEvent))
                .build();
        return request;
    }

    private VisibilityTypes extractVisibility(final APIGatewayProxyRequestEvent event) {
        if (event.getQueryStringParameters() != null) {
            return VisibilityTypes.fromString(
                    event.getQueryStringParameters()
                            .getOrDefault(Gear.VISIBILITY_FIELD, VisibilityTypes.PUBLIC.name()));
        }
        return VisibilityTypes.PUBLIC;
    }
}
