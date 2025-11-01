package com.tanveer.agent.infrastructure.service;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmbeddingService {

    private final EmbeddingModel embeddingModel;

    public EmbeddingService(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    /**
     * Generate an embedding for a given text.
     */
    public float[] generate(String content) {
        EmbeddingRequest request = new EmbeddingRequest(List.of(content),null);
        EmbeddingResponse response = embeddingModel.call(request);

        return response.getResult().getOutput(); // First embedding (since we passed one text)
    }

    /**
     * Optionally return only the vector if you want to store directly.
     */
    public float[] generateVector(String content) {
        return generate(content);
    }
}

