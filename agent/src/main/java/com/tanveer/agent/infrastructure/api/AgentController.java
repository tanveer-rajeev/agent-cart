package com.tanveer.agent.infrastructure.api;

import com.tanveer.agent.application.RAGUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/agent")
@Slf4j
public class AgentController {
    private final OllamaChatModel ollamaChatModel;
    private final RAGUseCase ragUseCase;
    private final VectorStore vectorStore;

    @GetMapping("/search")
    public ResponseEntity<List<Document>> searchFromVectorStore(@RequestParam String query) {
        return ResponseEntity.ok(ragUseCase.searchProducts(query));
    }

    @PostMapping("/chat")
    public String chat(@RequestBody String message) {
        log.info("Hitting Agent Chat api");
        return ChatClient.builder(ollamaChatModel)
                .build()
                .prompt()
                .advisors(new QuestionAnswerAdvisor(vectorStore))
                .user(message)
                .call()
                .content();
    }
}
