package com.tanveer.agent.application;

import org.springframework.ai.document.Document;

import java.util.List;

public interface RAGUseCase {
    void store(List<Document> documents);
    List<Document> searchProducts(String query);
}
