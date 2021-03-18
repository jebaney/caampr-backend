package com.jelistan.caampr.lambda;

import com.jelistan.caampr.lambda.model.*;

public class Constants {

    public static final String GEAR_ID = "gear-id-1";
    public static final String PROFILE_ID = "profile-id-1";
    public static final String CALLER_ID = "caller-id-1";

    public static final String GET_GEAR_LIST_URI = String.format("/user/%s/gear", PROFILE_ID);

    public static Gear FAKE_GEAR = Gear.builder()
            .gearId(GEAR_ID)
            .profileId(PROFILE_ID)
            .visibility(VisibilityTypes.PUBLIC)
            .type(GearTypes.GEAR)
            .name("Tent Stakes")
            .title("Gray Bunny Solid Steel Tent Stakes")
            .description(" Premium quality solid iron tent stake kit comes with a set of 12 pegs and a " +
                    "convenient carry bag to take when camping or backpacking.")
            .link(Link.builder()
                    .id(100)
                    .url("https://smile.amazon.com/gp/product/B07NS77PDQ")
                    .type(LinkTypes.AMAZON)
                    .build())
            .imageUrl("https://images-na.ssl-images-amazon.com/images/I/51y-NmeXHdL._AC_SL1000_.jpg")
            .build();

}
