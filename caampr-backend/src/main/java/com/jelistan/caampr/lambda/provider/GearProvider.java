package com.jelistan.caampr.lambda.provider;

import com.jelistan.caampr.lambda.model.Gear;
import com.jelistan.caampr.lambda.model.GearListRequest;
import com.jelistan.caampr.lambda.model.Link;
import com.jelistan.caampr.lambda.model.LinkTypes;

import java.util.List;

public interface GearProvider {

    public static Gear FAKE_GEAR = Gear.builder()
            .id("666")
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

    public List<Gear> getGearList(final GearListRequest request);

}
