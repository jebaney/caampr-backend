package com.jelistan.caampr.lambda.dao;

import com.jelistan.caampr.lambda.adaptor.GearAdaptor;
import com.jelistan.caampr.lambda.adaptor.GearListAdaptor;
import com.jelistan.caampr.lambda.model.external.Gear;
import com.jelistan.caampr.lambda.model.external.GearList;
import com.jelistan.caampr.lambda.model.internal.DynamoGear;
import com.jelistan.caampr.lambda.model.internal.DynamoGearList;
import com.jelistan.caampr.lambda.model.internal.GearListRequest;
import com.jelistan.caampr.lambda.model.internal.GearRequest;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Dao coordinator between the various sub-Dao fetchers.
 */

public class DaoManager {

    private final GearDao gearDao;
    private final GearListDao gearListDao;
    private final GearListAdaptor listAdaptor;
    private final GearAdaptor gearAdaptor;

    /**
     * Constructor for Dao Manager object, takes various sub-Dao fetchers as input, as well as various gear object adaptors.
     * @param gearDao
     * @param gearListDao
     * @param listAdaptor
     * @param gearAdaptor
     */
    @Inject
    public DaoManager(final GearDao gearDao, final GearListDao gearListDao, final GearListAdaptor listAdaptor, final GearAdaptor gearAdaptor){
        this.gearDao = gearDao;
        this.gearListDao = gearListDao;
        this.listAdaptor = listAdaptor;
        this.gearAdaptor = gearAdaptor;
    }

    /**
     * Method that first fetches a DynamoGearList from the database based on listId, then fetches the associated
     * DynamoGear objects that match the entries list, then wraps the whole thing together into a final populated
     * GearList object, which is then returns. Or if no matching list is found, it returns null.
     * @param request Contains information on what gear list to fetch from the server
     * @return
     */
    public GearList getGearList(final GearListRequest request){
        List<DynamoGearList> queriedList = gearListDao.getList(request);
        if(queriedList.size() == 1){ //meaning it found a match from the listid
            DynamoGearList rawList = queriedList.get(0);
            GearList unpopulatedList = listAdaptor.convert(rawList);
            List<Gear> entries = new ArrayList<>();
            if(rawList.getEntries() != null){
                for(String gearId : rawList.getEntries()){
                    GearRequest gearRequest = GearRequest.builder()
                            .gearId(gearId)
                            .callerId(request.getCallerId())
                            .type(request.getType())
                            .build();
                    List<DynamoGear> rawGear = gearDao.getGear(gearRequest);
                    if(rawGear.size() == 1){
                        Gear gear = gearAdaptor.convert(rawGear.get(0));
                        entries.add(gear);
                    }
                }
            }
            unpopulatedList.setEntries(entries);
            return unpopulatedList;
        }
        return null;
    }
}
