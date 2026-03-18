package com.vie.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RagRequest {
    @NotBlank(message = "问题不能为空")
    private String question;

    private String sessionId;

    private int maxResults = 3;
}

