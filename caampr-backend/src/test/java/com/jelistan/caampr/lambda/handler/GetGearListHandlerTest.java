package com.jelistan.caampr.lambda.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.common.collect.Lists;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jelistan.caampr.lambda.Constants;
import com.jelistan.caampr.lambda.adaptor.GearListRequestAdaptor;
import com.jelistan.caampr.lambda.dao.GearDao;
import com.jelistan.caampr.lambda.model.Gear;
import com.jelistan.caampr.lambda.model.GearListRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class GetGearListHandlerTest {

    @Mock
    private APIGatewayProxyRequestEvent requestEvent;

    @Mock
    private Context context;

    @Mock
    private GearDao gearDao;

    @Mock
    private GearListRequestAdaptor adaptor;

    private GetGearListHandler unit;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        when(gearDao.getList(any(GearListRequest.class)))
                .thenReturn(Lists.newArrayList(Constants.FAKE_GEAR));

        when(requestEvent.getPath()).thenReturn("/users/6969/gear/");

        when(adaptor.convert(any(APIGatewayProxyRequestEvent.class)))
                .thenReturn(GearListRequest.builder().profileId("6969").build());

        unit = new GetGearListHandler(gearDao, adaptor);
    }

    @Test
    public void testHappyPath() throws Exception {
        final APIGatewayProxyResponseEvent responseEvent = unit.handleRequest(requestEvent, context);

        assertEquals(200, responseEvent.getStatusCode().intValue());

        String jsonBody = responseEvent.getBody();
        assertNotNull(jsonBody);

        List<Gear> list = new GsonBuilder().create().fromJson(jsonBody,
                new TypeToken<List<Gear>>(){}.getType());

        assertEquals(1, list.size());
        assertEquals(Constants.FAKE_GEAR, list.get(0));
    }
}
