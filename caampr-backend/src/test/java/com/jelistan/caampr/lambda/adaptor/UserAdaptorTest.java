package com.jelistan.caampr.lambda.adaptor;

import com.jelistan.caampr.lambda.model.external.User;
import com.jelistan.caampr.lambda.model.internal.DynamoUser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.jelistan.caampr.lambda.Constants.PROFILE_ID;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class UserAdaptorTest {
    @Mock
    private DynamoUser internalUser;

    @Mock
    private User externalUser;

    private UserAdaptor unit;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        when(externalUser.getUserId()).thenReturn(PROFILE_ID);
        when(externalUser.getFirstName()).thenReturn("Chuck");
        when(externalUser.getLastName()).thenReturn("Testa");

        when(internalUser.getUserId()).thenReturn(PROFILE_ID);
        when(internalUser.getFirstName()).thenReturn("Chuck");
        when(internalUser.getLastName()).thenReturn("Testa");

        unit = new UserAdaptor();
    }

    @Test
    public void testConvertInternalToExternal() throws Exception {
        User external = unit.convert(internalUser);

        assertEquals(PROFILE_ID, external.getUserId());
        assertEquals("Chuck", external.getFirstName());
        assertEquals("Testa", external.getLastName());
    }

    @Test
    public void testConvertExternalToInternal() throws Exception {
        DynamoUser internal = unit.convert(externalUser);

        assertEquals(PROFILE_ID, internal.getUserId());
        assertEquals("Chuck", internal.getFirstName());
        assertEquals("Testa", internal.getLastName());
    }
}
