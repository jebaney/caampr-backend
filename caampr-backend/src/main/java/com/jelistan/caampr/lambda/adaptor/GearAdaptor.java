package com.jelistan.caampr.lambda.adaptor;

import com.jelistan.caampr.lambda.model.external.Gear;
import com.jelistan.caampr.lambda.model.internal.DynamoGear;

public class GearAdaptor {
    public DynamoGear convert(Gear externalGear){
        DynamoGear internalGear = DynamoGear.builder()
                .gearId(externalGear.getGearId())
                .userId(externalGear.getUserId())
                .visibility(externalGear.getVisibility())
                .name(externalGear.getName())
                .type(externalGear.getType())
                .description(externalGear.getDescription())
                .imageUrl(externalGear.getImageUrl())
                .link(externalGear.getLink())
                .build();
        return internalGear;
    }

    public Gear convert(DynamoGear internalGear){
        Gear externalGear = Gear.builder()
                .gearId(internalGear.getGearId())
                .userId(internalGear.getUserId())
                .visibility(internalGear.getVisibility())
                .name(internalGear.getName())
                .type(internalGear.getType())
                .description(internalGear.getDescription())
                .imageUrl(internalGear.getImageUrl())
                .link(internalGear.getLink())
                .build();
        return externalGear;
    }
}
