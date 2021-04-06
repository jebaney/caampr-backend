package com.jelistan.caampr.lambda.adaptor;

import com.jelistan.caampr.lambda.model.external.User;
import com.jelistan.caampr.lambda.model.internal.DynamoUser;

public class UserAdaptor {
    public DynamoUser convert(User externalUser){
        DynamoUser internalUser = DynamoUser.builder()
                .userId(externalUser.getUserId())
                .firstName(externalUser.getFirstName())
                .lastName(externalUser.getLastName())
                .build();
        return internalUser;
    }

    public User convert(DynamoUser internalUser){
        User externalUser = User.builder()
                .userId(internalUser.getUserId())
                .firstName(internalUser.getFirstName())
                .lastName(internalUser.getLastName())
                .build();
        return externalUser;
    }
}
