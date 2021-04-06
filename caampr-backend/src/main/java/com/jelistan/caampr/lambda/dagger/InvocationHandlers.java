package com.jelistan.caampr.lambda.dagger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

/**
 * Provides the handler methods that get defined in the cloudformation templates.
 * This allows us to ensure the dagger dependencies are all setup prior to
 * execution.  The handle methods just delegate to the real handlers and
 * allows us to have those handlers have injectible constructors.
 */
public class InvocationHandlers {

    private LambdaHandlers lambdaHandlers = DaggerLambdaHandlers.create();

    public APIGatewayProxyResponseEvent handleGetUserListRequest(
            final APIGatewayProxyRequestEvent event,
            final Context context) {
        return lambdaHandlers.getGearListHandler().handleRequest(event, context);
    }

}
