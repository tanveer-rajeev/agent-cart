package com.tanveer.agent.application;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RAGService implements RAGUseCase {

    private final VectorStore vectorStore;

    @Override
    public void store(List<Document> documents) {
        vectorStore.accept(documents);
    }

    @Override
    public List<Document> searchProducts(String query) {
        return vectorStore.similaritySearch(query);
    }
}
