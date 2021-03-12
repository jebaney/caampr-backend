package com.jelistan.caampr.lambda.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jelistan.caampr.lambda.adaptor.GearListRequestAdaptor;
import com.jelistan.caampr.lambda.model.Gear;
import com.jelistan.caampr.lambda.model.GearListRequest;
import com.jelistan.caampr.lambda.provider.GearProvider;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

/**
 * API for handling the fetching of lists of gear
 */
public class GetGearListHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final GearProvider gearProvider;
    private final GearListRequestAdaptor adaptor;

    @Inject
    public GetGearListHandler(final GearProvider gearProvider) {
        this.gearProvider = gearProvider;
        this.adaptor = new GearListRequestAdaptor();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent event, Context context)  {
        final GearListRequest gearListRequest = adaptor.convert(event);
        final Gson gson = new GsonBuilder().create();
        final List<Gear> list = gearProvider.getGearList(gearListRequest);

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withHeaders(Collections.emptyMap())
                .withBody(gson.toJson(list));
    }
}
