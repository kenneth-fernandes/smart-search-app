package com.smartsearchapp.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    private final DynamoDbClient dynamoDbClient;

    public SearchService(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public List<String> searchByKeyword(String keyword) {
        ScanRequest scanRequest = ScanRequest.builder().tableName("Test_Table").build();

        ScanResponse scanResponse = dynamoDbClient.scan(scanRequest);

        List<String> results = new ArrayList<>();

        scanResponse.items().forEach((item) -> {
            String data = item.get("data").s();
            if(data.contains(keyword)) {
                results.add(data);
            }
        });

        return results;
    }
}
