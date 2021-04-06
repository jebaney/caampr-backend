package com.jelistan.caampr.lambda.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.jelistan.caampr.lambda.Constants;
import com.jelistan.caampr.lambda.adaptor.GearListAdaptor;
import com.jelistan.caampr.lambda.adaptor.GearListRequestAdaptor;
import com.jelistan.caampr.lambda.dao.DaoManager;
import com.jelistan.caampr.lambda.model.internal.GearListRequest;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class GetGearListHandlerTest {

    @Mock
    private APIGatewayProxyRequestEvent requestEvent;

    @Mock
    private Context context;

    @Mock
    private DaoManager daoManager;

    @Mock
    private GearListRequestAdaptor adaptor;

    private GetGearListHandler unit;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        GearListAdaptor listAdaptor = new GearListAdaptor();

        when(daoManager.getGearList(any(GearListRequest.class)))
                .thenReturn(listAdaptor.convert(Constants.FAKE_LIST));

        when(requestEvent.getPath()).thenReturn("/lists/list-id-1/gear/");

        when(adaptor.convert(any(APIGatewayProxyRequestEvent.class)))
                .thenReturn(GearListRequest.builder().listId("list-id-1").build());

        unit = new GetGearListHandler(daoManager, adaptor);
    }
/*
    @Test
    public void testHappyPath() throws Exception {
        final APIGatewayProxyResponseEvent responseEvent = unit.handleRequest(requestEvent, context);

        assertEquals(200, responseEvent.getStatusCode().intValue());

        String jsonBody = responseEvent.getBody();
        assertNotNull(jsonBody);

        List<DynamoGear> list = new GsonBuilder().create().fromJson(jsonBody,
                new TypeToken<List<DynamoGear>>(){}.getType());

        assertEquals(1, list.size());
        assertEquals(Constants.FAKE_GEAR, list.get(0));
    }

 */
}
