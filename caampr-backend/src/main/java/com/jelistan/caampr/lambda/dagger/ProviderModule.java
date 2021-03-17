package com.jelistan.caampr.lambda.dagger;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.jelistan.caampr.lambda.adaptor.GearListRequestAdaptor;
import com.jelistan.caampr.lambda.provider.DynamoGearProvider;
import com.jelistan.caampr.lambda.provider.GearProvider;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ProviderModule {

    @Provides
    @Singleton
    GearProvider provideGearProvider(DynamoDBMapper mapper) {
        return new DynamoGearProvider(mapper);
    }

    @Provides
    @Singleton
    AmazonDynamoDB provideAmazonDynamoDB(AWSCredentialsProvider credentialsProvider){
        return AmazonDynamoDBClientBuilder.standard().withCredentials(credentialsProvider).build();
    }

    @Provides
    @Singleton
    DynamoDBMapper provideDynamoDBMapper(AmazonDynamoDB client){
        return new DynamoDBMapper(client);
    }

    @Provides
    @Singleton
    AWSCredentialsProvider provideAWSCredentialProvider() {
        return DefaultAWSCredentialsProviderChain.getInstance();
    }

    @Provides
    @Singleton
    GearListRequestAdaptor provideGearListRequestAdaptor(){
        return new GearListRequestAdaptor();
    }

}
