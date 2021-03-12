package com.jelistan.caampr.lambda.adaptor;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.jelistan.caampr.lambda.model.GearListRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.when;

public class GearListRequestAdaptorTest {
    @Mock
    private APIGatewayProxyRequestEvent requestEvent;

    private GearListRequestAdaptor adaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        when(requestEvent.getPath()).thenReturn("/user/666/gear");

        adaptor = new GearListRequestAdaptor();
    }

    @Test
    public void testGetGearList() throws Exception {
        GearListRequest request = adaptor.convert(requestEvent);

        assertEquals("6969", request.getCallerId());
        assertEquals("666", request.getProfileId());
    }


}
