package com.jelistan.caampr.lambda.adaptor;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.jelistan.caampr.lambda.model.internal.GearListRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static com.jelistan.caampr.lambda.Constants.GET_GEAR_LIST_URI;
import static com.jelistan.caampr.lambda.Constants.PROFILE_ID;
import static com.jelistan.caampr.lambda.model.internal.GearTypes.GEAR;
import static com.jelistan.caampr.lambda.model.internal.VisibilityTypes.PUBLIC;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class GearListRequestAdaptorTest {
    @Mock
    private APIGatewayProxyRequestEvent requestEvent;

    private GearListRequestAdaptor unit;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        when(requestEvent.getPath()).thenReturn(GET_GEAR_LIST_URI);
        when(requestEvent.getQueryStringParameters()).thenReturn(Collections.emptyMap());

        unit = new GearListRequestAdaptor();
    }

    @Test
    public void testGetUserList() throws Exception {
        GearListRequest request = unit.convert(requestEvent);

        assertEquals("6969", request.getCallerId());
        assertEquals(PROFILE_ID, request.getListId());
        assertEquals(PUBLIC, request.getVisibility());
        assertEquals(GEAR, request.getType());
    }


}
