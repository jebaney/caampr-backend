package com.jelistan.caampr.lambda.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.jelistan.caampr.lambda.model.internal.DynamoGearList;
import com.jelistan.caampr.lambda.model.internal.GearListRequest;
import com.jelistan.caampr.lambda.model.internal.GearTypes;
import com.jelistan.caampr.lambda.model.internal.VisibilityTypes;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;
import java.util.Optional;

import static com.jelistan.caampr.lambda.Constants.CALLER_ID;
import static com.jelistan.caampr.lambda.Constants.LIST_ID;
import static com.jelistan.caampr.lambda.model.internal.DynamoGear.VISIBILITY_FIELD;
import static com.jelistan.caampr.lambda.model.internal.VisibilityTypes.PUBLIC;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class GearListDaoTest {

    @Mock
    private DynamoDBMapper mapper;

    @Mock
    private PaginatedQueryList<DynamoGearList> queryList;

    @Captor
    private ArgumentCaptor<DynamoDBQueryExpression<DynamoGearList>> queryExpressionCaptor;

    private GearListDao unit;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        when(mapper.query(eq(DynamoGearList.class), queryExpressionCaptor.capture()))
                .thenReturn(queryList);

        unit = new GearListDao(mapper);
    }

    @Test
    public void testPublicGearType() {
        final GearListRequest request = GearListRequest.builder()
                .callerId(CALLER_ID)
                .listId(LIST_ID)
                .visibility(PUBLIC)
                .type(GearTypes.GEAR)
                .build();

        unit.getList(request);

        final DynamoDBQueryExpression<DynamoGearList> expression = queryExpressionCaptor.getValue();
        assertNotNull(expression);

        // Make sure the values we passed in are properly set
        assertEquals(LIST_ID, expression.getHashKeyValues().getListId());

        // Check the visibility filter
        assertVisibilityFilter(expression, Optional.of(PUBLIC));
    }

    private void assertVisibilityFilter(final DynamoDBQueryExpression<DynamoGearList> expression,
                                        final Optional<VisibilityTypes> visibility) {
        final Map<String, Condition> filters = expression.getQueryFilter();
        assertNotNull(filters);
        if (visibility.isPresent() && visibility.get() == PUBLIC) {
            assertEquals(1, filters.size());
            assertTrue(filters.containsKey(VISIBILITY_FIELD));
            final Condition visibilityFilter = filters.get(VISIBILITY_FIELD);
            assertEquals(ComparisonOperator.EQ.name(), visibilityFilter.getComparisonOperator());
            assertEquals(visibility.get().name(), visibilityFilter.getAttributeValueList().get(0).getS());
        } else {
            // If not present or private, there should not be a filter as it indicates "all"
            assertFalse(filters.containsKey(VISIBILITY_FIELD));
        }
    }

}
