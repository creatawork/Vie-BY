package com.vie.starter.controller;

import com.vie.service.common.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vie.service.dto.RagDocumentAddRequest;
import com.vie.service.dto.RagRequest;
import com.vie.service.dto.RagResponse;
import com.vie.service.service.RagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/rag")
@RequiredArgsConstructor
public class RagController {

    private final RagService ragService;
    private final ObjectMapper objectMapper;

    @PostMapping("/query")
    public Result<RagResponse> query(@Valid @RequestBody RagRequest request) {
        return Result.success(ragService.query(request));
    }

    @PostMapping("/query-raw")
    public Result<RagResponse> queryRaw(@RequestBody String rawBody) throws IOException {
        RagRequest request = objectMapper.readValue(rawBody, RagRequest.class);
        return Result.success(ragService.query(request));
    }

    @PostMapping("/query-tolerant")
    public Result<RagResponse> queryTolerant(@RequestBody String rawBody) throws IOException {
        RagRequest request = objectMapper.readValue(fixJsonIfNeeded(rawBody), RagRequest.class);
        return Result.success(ragService.query(request));
    }

    private String fixJsonIfNeeded(String rawBody) {
        String trimmed = rawBody == null ? "" : rawBody.trim();
        if (trimmed.startsWith("{") && trimmed.contains("\"") || trimmed.contains("\":\"")) {
            return trimmed;
        }
        String normalized = trimmed;
        normalized = normalized.replaceAll("\\bquestion\\b", "\"question\"");
        normalized = normalized.replaceAll("\\bmaxResults\\b", "\"maxResults\"");
        Pattern pattern = Pattern.compile("\\\"question\\\"\\s*:\\s*([^,}]+)");
        Matcher matcher = pattern.matcher(normalized);
        if (matcher.find()) {
            String value = matcher.group(1).trim();
            if (!value.startsWith("\"")) {
                normalized = matcher.replaceFirst("\"question\":\"" + value + "\"");
            }
        }
        return normalized;
    }

    @PostMapping("/add")
    public Result<Void> addDocument(@Valid @RequestBody RagDocumentAddRequest request) {
        ragService.addDocument(request);
        return Result.success("文档已添加", null);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file,
                                         @RequestParam(value = "metadata", required = false) String metadata) {
        try {
            String content = new String(file.getBytes());
            RagDocumentAddRequest request = new RagDocumentAddRequest();
            request.setContent(content);
            request.setMetadata(metadata == null ? file.getOriginalFilename() : metadata);
            ragService.addDocument(request);
            return ResponseEntity.ok("文档上传并处理成功");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("文件处理失败: " + e.getMessage());
        }
    }

    @GetMapping("/health")
    public Result<String> health() {
        return Result.success("RAG服务运行正常");
    }
}

