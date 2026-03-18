package com.vie.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiChatProductCard {
    private Long productId;
    private String productName;
    private String mainImage;
    private BigDecimal currentPrice;
    private String description;
    private String detailUrl;
}

