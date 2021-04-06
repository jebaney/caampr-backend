package com.jelistan.caampr.lambda.adaptor;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.jelistan.caampr.lambda.model.internal.DynamoGear;
import com.jelistan.caampr.lambda.model.internal.GearListRequest;
import com.jelistan.caampr.lambda.model.internal.GearTypes;
import com.jelistan.caampr.lambda.model.internal.VisibilityTypes;

/**
 * Converts APIGatewayProxyRequestEvent (external) to GearListRequest (internal)
 * path: /list/[listId]/gear
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
        String listId = tokens[2];
        String type = tokens[3];
        GearListRequest request = GearListRequest.builder()
                .callerId("6969") //TODO temporary hardcode until requester info is implemented, see backend issue #18
                .listId(listId)
                .type(GearTypes.fromString(type))
                .visibility(extractVisibility(requestEvent))
                .build();
        return request;
    }

    private VisibilityTypes extractVisibility(final APIGatewayProxyRequestEvent event) {
        if (event.getQueryStringParameters() != null) {
            return VisibilityTypes.fromString(
                    event.getQueryStringParameters()
                            .getOrDefault(DynamoGear.VISIBILITY_FIELD, VisibilityTypes.PUBLIC.name()));
        }
        return VisibilityTypes.PUBLIC;
    }
}
