package com.jelistan.caampr.lambda.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jelistan.caampr.lambda.adaptor.GearListRequestAdaptor;
import com.jelistan.caampr.lambda.dao.GearDao;
import com.jelistan.caampr.lambda.model.Gear;
import com.jelistan.caampr.lambda.model.GearListRequest;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

/**
 * API for handling the fetching of lists of gear
 */
@Slf4j
public class GetGearListHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final GearDao dao;
    private final GearListRequestAdaptor adaptor;

    @Inject
    public GetGearListHandler(final GearDao dao, final GearListRequestAdaptor adaptor) {
        this.dao = dao;
        this.adaptor = adaptor;
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent event, Context context)  {
        final GearListRequest gearListRequest = adaptor.convert(event);
        log.info("Handling getGearList request [{}]", gearListRequest);
        final Gson gson = new GsonBuilder().create();
        final List<Gear> list = dao.getList(gearListRequest);

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withHeaders(Collections.emptyMap())
                .withBody(gson.toJson(list));
    }
}
