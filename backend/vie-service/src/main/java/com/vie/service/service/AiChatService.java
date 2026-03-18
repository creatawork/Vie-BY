package com.vie.service.service;

import com.vie.service.dto.AiChatRequest;
import com.vie.service.dto.AiChatResponse;
import reactor.core.publisher.Flux;

public interface AiChatService {
    AiChatResponse chat(AiChatRequest request);

    Flux<AiChatResponse> streamChat(AiChatRequest request);
}

