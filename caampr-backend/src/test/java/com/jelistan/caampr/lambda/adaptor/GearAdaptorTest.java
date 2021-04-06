package com.jelistan.caampr.lambda.adaptor;

import com.jelistan.caampr.lambda.model.external.Gear;
import com.jelistan.caampr.lambda.model.internal.DynamoGear;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.jelistan.caampr.lambda.Constants.FAKE_GEAR;
import static com.jelistan.caampr.lambda.Constants.GEAR_ID;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class GearAdaptorTest {

    @Mock
    private Gear externalGear;

    private GearAdaptor unit;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        when(externalGear.getGearId()).thenReturn(GEAR_ID);
        when(externalGear.getDescription()).thenReturn(FAKE_GEAR.getDescription());
        when(externalGear.getType()).thenReturn(FAKE_GEAR.getType());
        when(externalGear.getVisibility()).thenReturn(FAKE_GEAR.getVisibility());
        when(externalGear.getUserId()).thenReturn(FAKE_GEAR.getUserId());
        when(externalGear.getName()).thenReturn(FAKE_GEAR.getName());
        when(externalGear.getLink()).thenReturn(FAKE_GEAR.getLink());
        when(externalGear.getImageUrl()).thenReturn(FAKE_GEAR.getImageUrl());

        unit = new GearAdaptor();
    }

    @Test
    public void testConvertInternalToExternal() throws Exception {
        Gear external = unit.convert(FAKE_GEAR);

        assertEquals(FAKE_GEAR.getGearId(), external.getGearId());
        assertEquals(FAKE_GEAR.getDescription(), external.getDescription());
        assertEquals(FAKE_GEAR.getType(), external.getType());
        assertEquals(FAKE_GEAR.getVisibility(), external.getVisibility());
        assertEquals(FAKE_GEAR.getUserId(), external.getUserId());
        assertEquals(FAKE_GEAR.getName(), external.getName());
        assertEquals(FAKE_GEAR.getLink(), external.getLink());
        assertEquals(FAKE_GEAR.getImageUrl(), external.getImageUrl());
    }

    @Test
    public void testConvertExternalToInternal() throws Exception {
        DynamoGear internal = unit.convert(externalGear);

        assertEquals(internal, FAKE_GEAR);
    }
}
