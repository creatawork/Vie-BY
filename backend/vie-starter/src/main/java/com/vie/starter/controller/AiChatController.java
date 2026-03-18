package com.vie.starter.controller;

import com.vie.service.common.Result;
import com.vie.service.dto.AiChatRequest;
import com.vie.service.dto.AiChatResponse;
import com.vie.service.service.AiChatService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiChatController {

    private final AiChatService aiChatService;

    @PostMapping("/chat")
    public Result<AiChatResponse> chat(@Valid @RequestBody AiChatRequest request) {
        return Result.success(aiChatService.chat(request));
    }

    @PostMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamChat(@Valid @RequestBody AiChatRequest request, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("X-Accel-Buffering", "no");

        SseEmitter emitter = new SseEmitter(0L);
        aiChatService.streamChat(request)
            .doOnNext(chunk -> sendChunk(emitter, chunk))
            .doOnError(ex -> completeWithError(emitter, ex))
            .doOnComplete(emitter::complete)
            .subscribe();
        return emitter;
    }

    private void sendChunk(SseEmitter emitter, AiChatResponse chunk) {
        try {
            emitter.send(SseEmitter.event().data(chunk));
        } catch (IOException ex) {
            emitter.completeWithError(ex);
        }
    }

    private void completeWithError(SseEmitter emitter, Throwable ex) {
        try {
            emitter.send(SseEmitter.event().data(Result.error(ex.getMessage())));
        } catch (IOException ioException) {
            emitter.completeWithError(ioException);
            return;
        }
        emitter.completeWithError(ex);
    }
}

