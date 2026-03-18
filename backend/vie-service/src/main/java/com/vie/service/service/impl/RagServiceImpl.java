package com.vie.service.service.impl;

import com.vie.service.dto.RagDocumentAddRequest;
import com.vie.service.dto.RagRequest;
import com.vie.service.dto.RagResponse;
import com.vie.service.service.RagService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RagServiceImpl implements RagService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    @Override
    public RagResponse query(RagRequest request) {
        int maxResults = request.getMaxResults() > 0 ? request.getMaxResults() : 3;
        List<Document> similarDocuments = vectorStore.similaritySearch(
            SearchRequest.query(request.getQuestion()).withTopK(maxResults)
        );

        String context = similarDocuments.stream()
            .map(Document::getContent)
            .collect(Collectors.joining("\n\n"));

        String prompt = String.format(
            "基于以下上下文信息回答问题：\n\n%s\n\n问题：%s\n\n如果上下文信息不足以回答问题，请说明信息不足。",
            context,
            request.getQuestion()
        );

        Prompt chatPrompt = new Prompt(prompt);
        String answer = chatClient.call(chatPrompt).getResult().getOutput().getContent();

        return RagResponse.builder()
            .answer(answer)
            .sourceDocuments(similarDocuments.stream().map(Document::getContent).toList())
            .confidence(calculateConfidence(answer))
            .sessionId(request.getSessionId())
            .build();
    }

    @Override
    public void addDocument(RagDocumentAddRequest request) {
        TokenTextSplitter splitter = new TokenTextSplitter();
        List<Document> documents = splitter.apply(List.of(
            new Document(request.getContent(), Map.of("source", defaultMetadata(request.getMetadata())))
        ));
        vectorStore.add(documents);
    }

    private double calculateConfidence(String answer) {
        String lower = answer == null ? "" : answer.toLowerCase();
        if (lower.contains("信息不足") || lower.contains("无法确定")) {
            return 0.3;
        }
        return 0.9;
    }

    private String defaultMetadata(String metadata) {
        if (metadata == null || metadata.isBlank()) {
            return "manual";
        }
        return metadata;
    }
}

