package com.vie.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RagDocumentAddRequest {
    @NotBlank(message = "文档内容不能为空")
    private String content;

    private String metadata;
}

