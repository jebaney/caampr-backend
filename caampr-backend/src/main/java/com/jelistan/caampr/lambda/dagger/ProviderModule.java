package com.jelistan.caampr.lambda.dagger;

import com.jelistan.caampr.lambda.provider.GearProvider;
import com.jelistan.caampr.lambda.provider.GearListProvider;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ProviderModule {

    @Provides
    @Singleton
    GearProvider provideGearProvider() {
        return new GearListProvider();
    }

}
