package com.jelistan.caampr.lambda.adaptor;

import com.jelistan.caampr.lambda.model.external.Gear;
import com.jelistan.caampr.lambda.model.external.GearList;
import com.jelistan.caampr.lambda.model.internal.DynamoGearList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static com.jelistan.caampr.lambda.Constants.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class GearListAdaptorTest {
    private DynamoGearList internalGearList;

    @Mock
    private GearList externalGearList;

    private List<Gear> externalEntries;
    private List<String> internalEntries;

    private GearAdaptor gearAdaptor;

    private GearListAdaptor unit;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        externalEntries = new ArrayList<>();
        gearAdaptor = new GearAdaptor();
        externalEntries.add(gearAdaptor.convert(FAKE_GEAR));

        internalEntries = new ArrayList<>();
        internalEntries.add(FAKE_GEAR.getGearId());

        internalGearList = FAKE_LIST;
        internalGearList.setEntries(internalEntries);
        when(externalGearList.getListId()).thenReturn(LIST_ID);
        when(externalGearList.getUserId()).thenReturn(PROFILE_ID);
        when(externalGearList.getDescription()).thenReturn(FAKE_LIST.getDescription());
        when(externalGearList.getVisibility()).thenReturn(FAKE_LIST.getVisibility());
        when(externalGearList.getName()).thenReturn(FAKE_LIST.getName());
        when(externalGearList.getEntries()).thenReturn(externalEntries);

        unit = new GearListAdaptor();
    }

    @Test
    public void testConvertInternalToExternal() throws Exception {
        GearList external = unit.convert(internalGearList);

        assertEquals(FAKE_LIST.getListId(), external.getListId());
        assertEquals(FAKE_LIST.getDescription(), external.getDescription());
        assertEquals(FAKE_LIST.getVisibility(), external.getVisibility());
        assertEquals(FAKE_LIST.getUserId(), external.getUserId());
        assertEquals(FAKE_LIST.getName(), external.getName());
        assertEquals(null, external.getEntries());
    }

    @Test
    public void testConvertExternalToInternal() throws Exception {
        DynamoGearList internal = unit.convert(externalGearList);

        assertEquals(internal, FAKE_LIST);
    }
}
