package com.smartsearchapp.controller;

import com.smartsearchapp.model.KnowledgeBaseRecord;
import com.smartsearchapp.service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public List<KnowledgeBaseRecord> search(@RequestParam String keyword) {
        return searchService.searchByKeyword(keyword);
    }
}
