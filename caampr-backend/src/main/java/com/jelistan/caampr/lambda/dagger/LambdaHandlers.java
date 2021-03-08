package com.jelistan.caampr.lambda.dagger;

import com.jelistan.caampr.lambda.handler.GetGearListHandler;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
    ProviderModule.class
})
/**
 * top level component for dagger dependency injection.  dagger creates
 * a concrete class for this called DaggerLambdaHandler.
 *
 * Define handlers here, then add handler methods that delegate to these
 * in the {@link InvocationHandlers} class.
 */
public interface LambdaHandlers {
    GetGearListHandler getGearListHandler();
}
