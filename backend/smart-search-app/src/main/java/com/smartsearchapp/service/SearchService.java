package com.smartsearchapp.service;

import com.smartsearchapp.model.KnowledgeBaseRecord;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {

    private final DynamoDbClient dynamoDbClient;

    public SearchService(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public List<KnowledgeBaseRecord> searchByKeyword(String keyword) {

        String lowerCaseKeyword = keyword.toLowerCase();

        ScanRequest scanRequest = ScanRequest.builder().tableName("KnowledgeBase").build();

        ScanResponse scanResponse = dynamoDbClient.scan(scanRequest);

        List<KnowledgeBaseRecord> matchingRecords = new ArrayList<>();

        scanResponse.items().forEach((item) -> {
            if(keyword.equals("*") || isKeywordMatch(item, lowerCaseKeyword)) {
                matchingRecords.add(KnowledgeBaseRecord.fromDynamoDB(item));
            }
        });

        return matchingRecords;
    }

    private boolean isKeywordMatch(Map<String, AttributeValue> item, String keyword) {
        if(containsIgnoreCase(item.get("title"), keyword)
                || containsIgnoreCase(item.get("content"), keyword)
                || containsIgnoreCase(item.get("category"), keyword)
                || containsIgnoreCase(item.get("author"), keyword)) {
            return true;
        }

        if(item.containsKey("tags")) {
            List<AttributeValue> tags = item.get("tags").l();
            return tags.stream().anyMatch(tag -> containsIgnoreCase(tag, keyword));
        }

        return  false;
    }

    private boolean containsIgnoreCase(AttributeValue value, String keyword) {
        return value != null && value.s() != null && value.s().toLowerCase().contains(keyword);
    }
}
