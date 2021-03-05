package com.jelistan.caampr.lambda.external;

import com.jelistan.caampr.lambda.provider.GearProvider;

public class ProviderModule {

    public static GearProvider provideGearProvider() {
        return new GearProvider();
    }

}
