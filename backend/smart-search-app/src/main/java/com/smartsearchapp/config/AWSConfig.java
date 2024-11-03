package com.smartsearchapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.transcribe.TranscribeClient;

@Configuration
public class AWSConfig {

    @Value("${aws.access-key-id}")
    private String awsAccessKeyID;

    @Value("${aws.secret-access-key}")
    private String awsSecretAccessKey;

    @Value("${aws.region:us-east-1}") // default to us-east-1 if not set
    private String awsRegion;

    @Bean
    public AwsBasicCredentials awsCredentials() {
        return AwsBasicCredentials.create(awsAccessKeyID, awsSecretAccessKey);
    }

    @Bean
    public DynamoDbClient dynamoDbClient(AwsBasicCredentials awsCredentials) {
        return DynamoDbClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .region(Region.of(awsRegion))
                .build();
    }

    @Bean
    public TranscribeClient amazonTranscribe(AwsBasicCredentials awsCredentials) {
        return TranscribeClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .region(Region.of(awsRegion))
                .build();
    }
}