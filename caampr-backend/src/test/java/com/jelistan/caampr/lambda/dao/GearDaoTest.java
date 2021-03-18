package com.jelistan.caampr.lambda.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.jelistan.caampr.lambda.model.Gear;
import com.jelistan.caampr.lambda.model.GearListRequest;
import com.jelistan.caampr.lambda.model.GearTypes;
import com.jelistan.caampr.lambda.model.VisibilityTypes;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;
import java.util.Optional;

import static com.jelistan.caampr.lambda.Constants.CALLER_ID;
import static com.jelistan.caampr.lambda.Constants.PROFILE_ID;
import static com.jelistan.caampr.lambda.model.Gear.TYPE_FIELD;
import static com.jelistan.caampr.lambda.model.Gear.VISIBILITY_FIELD;
import static com.jelistan.caampr.lambda.model.VisibilityTypes.PUBLIC;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class GearDaoTest {

    @Mock
    private DynamoDBMapper mapper;

    @Mock
    private PaginatedQueryList<Gear> queryList;

    @Captor
    private ArgumentCaptor<DynamoDBQueryExpression<Gear>> queryExpressionCaptor;

    private GearDao unit;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        when(mapper.query(eq(Gear.class), queryExpressionCaptor.capture()))
                .thenReturn(null);

        unit = new GearDao(mapper);
    }

    @Test
    public void testPublicGearType() {
        final GearListRequest request = GearListRequest.builder()
                .callerId(CALLER_ID)
                .profileId(PROFILE_ID)
                .visibility(PUBLIC)
                .type(GearTypes.GEAR)
                .build();

        unit.getList(request);

        final DynamoDBQueryExpression<Gear> expression = queryExpressionCaptor.getValue();
        assertNotNull(expression);

        // Make sure the values we passed in are properly set
        assertEquals(PROFILE_ID, expression.getHashKeyValues().getProfileId());

        // Check the range key
        assertRangeKey(expression, Optional.of(GearTypes.GEAR));

        // Check the visibility filter
        assertVisibilityFilter(expression, Optional.of(PUBLIC));
    }

    private void assertRangeKey(final DynamoDBQueryExpression<Gear> expression, final Optional<GearTypes> type) {
        final Map<String, Condition> rangeConditions = expression.getRangeKeyConditions();
        assertNotNull(rangeConditions);
        if (type.isPresent()) {
            assertTrue(rangeConditions.containsKey(TYPE_FIELD));
            final Condition rangeCondition = rangeConditions.get(TYPE_FIELD);
            assertEquals(ComparisonOperator.EQ.name(), rangeCondition.getComparisonOperator());
            assertEquals(type.get().name(), rangeCondition.getAttributeValueList().get(0).getS());
        } else {
            // Fetch all types
            assertFalse(rangeConditions.containsKey(TYPE_FIELD));
        }
    }

    private void assertVisibilityFilter(final DynamoDBQueryExpression<Gear> expression,
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
