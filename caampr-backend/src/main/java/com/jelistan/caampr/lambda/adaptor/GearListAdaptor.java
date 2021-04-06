package com.jelistan.caampr.lambda.adaptor;

import com.jelistan.caampr.lambda.model.external.Gear;
import com.jelistan.caampr.lambda.model.external.GearList;
import com.jelistan.caampr.lambda.model.internal.DynamoGearList;

import java.util.ArrayList;
import java.util.List;

public class GearListAdaptor {
    public DynamoGearList convert(GearList externalGearList){
        List<String> entries = new ArrayList<>();
        for(Gear gear : externalGearList.getEntries()){
            entries.add(gear.getGearId());
        }
        DynamoGearList internalUserList = DynamoGearList.builder()
                .userId(externalGearList.getUserId())
                .listId(externalGearList.getListId())
                .name(externalGearList.getName())
                .description(externalGearList.getDescription())
                .visibility(externalGearList.getVisibility())
                .entries(entries)
                .build();
        return internalUserList;
    }
    public GearList convert(DynamoGearList internalGearList){
        GearList externalUserList = GearList.builder()
                .userId(internalGearList.getUserId())
                .listId(internalGearList.getListId())
                .name(internalGearList.getName())
                .description(internalGearList.getDescription())
                .visibility(internalGearList.getVisibility())
                //.entries is intentionally not here because external entries requires server call to populate from id's
                .build();
        return externalUserList;
    }
}
