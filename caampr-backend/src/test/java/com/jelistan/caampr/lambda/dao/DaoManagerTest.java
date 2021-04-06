package com.jelistan.caampr.lambda.dao;

import com.jelistan.caampr.lambda.adaptor.GearAdaptor;
import com.jelistan.caampr.lambda.adaptor.GearListAdaptor;
import com.jelistan.caampr.lambda.model.external.Gear;
import com.jelistan.caampr.lambda.model.external.GearList;
import com.jelistan.caampr.lambda.model.internal.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static com.jelistan.caampr.lambda.Constants.*;
import static com.jelistan.caampr.lambda.model.internal.VisibilityTypes.PUBLIC;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class DaoManagerTest {
    @Mock
    private GearDao gearDao;

    @Mock
    private GearListDao listDao;

    @Mock
    private GearAdaptor gearAdaptor;

    @Mock
    private GearListAdaptor listAdaptor;

    @Mock
    private List<DynamoGearList> queryListNoMatch;

    @Mock
    private List<DynamoGearList> queryListEmpty;

    @Mock
    private List<DynamoGearList> queryListFull;

    @Mock
    private List<DynamoGear> gearDaoReturn;

    @Mock
    private GearList mockGearList;

    private List<DynamoGearList> mockedFullList;

    private List<Gear> fullGearEntries;

    List<Gear> emptyGearEntries;

    @Mock
    private Gear mockGear;

    private DaoManager unit;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        List<String> fullEntries = new ArrayList<>();
        fullEntries.add("fakegear_1");
        fullEntries.add("fakegear_2");

        mockedFullList = new ArrayList<>();
        mockedFullList.add(FAKE_LIST);
        mockedFullList.get(0).setEntries(fullEntries);

        fullGearEntries = new ArrayList<>();
        fullGearEntries.add(Gear.builder().build());
        fullGearEntries.add(Gear.builder().build());

        emptyGearEntries = new ArrayList<>();

        when(queryListNoMatch.size()).thenReturn(0);
        when(queryListEmpty.size()).thenReturn(1);
        when(queryListFull.size()).thenReturn(1);
        when(queryListEmpty.get(0)).thenReturn(FAKE_LIST);

        when(gearDao.getGear(any(GearRequest.class))).thenReturn(gearDaoReturn);

        when(listAdaptor.convert(any(DynamoGearList.class))).thenReturn(mockGearList);
        when(gearAdaptor.convert(any(DynamoGear.class))).thenReturn(mockGear);

        when(mockGearList.getListId()).thenReturn(LIST_ID);
        when(mockGearList.getVisibility()).thenReturn(PUBLIC);

        unit = new DaoManager(gearDao, listDao, listAdaptor, gearAdaptor);
    }

    @Test
    public void testEmptyList() {
        when(listDao.getList(any(GearListRequest.class))).thenReturn(queryListEmpty);
        when(mockGearList.getEntries()).thenReturn(emptyGearEntries);

        final GearListRequest request = GearListRequest.builder()
                .callerId(CALLER_ID)
                .listId(LIST_ID)
                .visibility(PUBLIC)
                .type(GearTypes.GEAR)
                .build();

        GearList gearList = unit.getGearList(request);

        assertProperListFields(gearList);
        assertEquals(0, gearList.getEntries().size());
    }

    @Test
    public void testFullList() {
        when(listDao.getList(any(GearListRequest.class))).thenReturn(mockedFullList);
        when(mockGearList.getEntries()).thenReturn(fullGearEntries);

        final GearListRequest request = GearListRequest.builder()
                .callerId(CALLER_ID)
                .listId(LIST_ID)
                .visibility(PUBLIC)
                .type(GearTypes.GEAR)
                .build();

        GearList gearList = unit.getGearList(request);

        assertProperListFields(gearList);
        assertEquals(2, gearList.getEntries().size());
    }

    @Test
    public void testNoMatchList() {
        when(listDao.getList(any(GearListRequest.class))).thenReturn(queryListNoMatch);

        final GearListRequest request = GearListRequest.builder()
                .callerId(CALLER_ID)
                .listId(LIST_ID)
                .visibility(PUBLIC)
                .type(GearTypes.GEAR)
                .build();

        GearList gearList = unit.getGearList(request);

        assertNull(gearList);
    }

    public void assertProperListFields(GearList list){
        assertNotNull(list);
        assertEquals(LIST_ID, list.getListId());
        assertEquals(PUBLIC, list.getVisibility());
    }
}
