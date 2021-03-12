package com.jelistan.caampr.lambda.provider;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.jelistan.caampr.lambda.model.Gear;
import com.jelistan.caampr.lambda.model.GearListRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class DynamoGearProviderTest {
    @Mock
    private GearListRequest request;

    @Mock
    private DynamoDBMapper mapper;

    @Mock
    private PaginatedQueryList<Gear> queryList;

    private DynamoGearProvider unit;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(queryList.size()).thenReturn(1);
        when(queryList.get(0)).thenReturn(GearProvider.FAKE_GEAR);

        when(mapper.query(any(), any(DynamoDBQueryExpression.class)))
                .thenReturn(queryList);

        unit = new DynamoGearProvider(mapper);
    }

    @Test
    public void testGetGearList() throws Exception {
        List<Gear> list = unit.getGearList(request);

        assertEquals(1, list.size());
        assertEquals(GearProvider.FAKE_GEAR, list.get(0));
    }
}
