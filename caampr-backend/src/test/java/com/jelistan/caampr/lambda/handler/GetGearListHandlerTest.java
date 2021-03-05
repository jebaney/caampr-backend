package com.jelistan.caampr.lambda.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.common.collect.Lists;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jelistan.caampr.lambda.model.Gear;
import com.jelistan.caampr.lambda.provider.GearProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class GetGearListHandlerTest {

    @Mock
    private APIGatewayProxyRequestEvent requestEvent;

    @Mock
    private Context context;

    @Mock
    private GearProvider gearProvider;

    private GetGearListHandler unit;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        when(gearProvider.getGearList(anyInt()))
                .thenReturn(Lists.newArrayList(GearProvider.FAKE_GEAR));

        unit = new GetGearListHandler(gearProvider);
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
        assertEquals(GearProvider.FAKE_GEAR, list.get(0));
    }
}
