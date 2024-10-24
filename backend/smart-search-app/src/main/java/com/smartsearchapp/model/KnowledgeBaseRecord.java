package com.smartsearchapp.model;

import lombok.Builder;
import lombok.Data;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class KnowledgeBaseRecord {
    private String id;
    private String title;
    private String content;
    private String category;
    private List<String> tags;
    private String author;
    private String dateCreated;
    private String lastUpdated;
    private String accessLevel;
    private int views;

    // Convert from DynamoDB item to KnowledgeBaseRecord model
    public static KnowledgeBaseRecord fromDynamoDB(Map<String, AttributeValue> item) {
        return KnowledgeBaseRecord.builder()
                .id(item.get("id").s())
                .title(item.get("title").s())
                .content(item.get("content").s())
                .category(item.get("category").s())
                .tags(item.get("tags").l().stream().map(AttributeValue::s).toList())
                .author(item.get("author").s())
                .dateCreated(item.get("date_created").s())
                .lastUpdated(item.get("last_updated").s())
                .accessLevel(item.get("access_level").s())
                .views(Integer.parseInt(item.get("views").n()))
                .build();
    }
}
