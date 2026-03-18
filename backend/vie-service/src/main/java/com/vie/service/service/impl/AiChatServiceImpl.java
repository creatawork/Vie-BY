package com.vie.service.service.impl;

import com.vie.service.dto.AiChatProductCard;
import com.vie.service.dto.AiChatRequest;
import com.vie.service.dto.AiChatResponse;
import com.vie.service.service.AiChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.StreamingChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AiChatServiceImpl implements AiChatService {

    private static final String DEFAULT_SYSTEM_PROMPT = "你是VIE平台的智能助手，请用简洁、专业、友好的语气回答用户问题。" +
        "如果用户问题与平台服务、商品、订单、物流、售后相关，优先给出清晰的流程和建议。" +
        "不要编造不存在的功能或数据，如不确定请说明。";

    private static final String PRODUCT_PROMPT = "你是VIE平台智能助手。" +
        "以下是可能相关的商品信息（仅供参考），请优先基于这些商品信息回答。" +
        "如果商品信息不足以回答问题，请明确说明。" +
        "不要编造商品信息或价格。";

    private static final int DEFAULT_PRODUCT_TOP_K = 5;

    private final ChatClient chatClient;
    private final StreamingChatClient streamingChatClient;
    private final VectorStore vectorStore;

    @Override
    public AiChatResponse chat(AiChatRequest request) {
        String question = request.getQuestion();
        List<AiChatProductCard> products = searchProductCards(question);
        String context = buildProductContext(products);

        Prompt prompt = new Prompt(List.of(
            new SystemMessage(PRODUCT_PROMPT + context),
            new UserMessage(question)
        ));
        String answer = chatClient.call(prompt).getResult().getOutput().getContent();

        return AiChatResponse.builder()
            .answer(answer)
            .sessionId(request.getSessionId())
            .products(products)
            .build();
    }

    @Override
    public Flux<AiChatResponse> streamChat(AiChatRequest request) {
        String question = request.getQuestion();
        List<AiChatProductCard> products = searchProductCards(question);
        String context = buildProductContext(products);

        Prompt prompt = new Prompt(List.of(
            new SystemMessage(PRODUCT_PROMPT + context),
            new UserMessage(question)
        ));

        return streamingChatClient.stream(prompt)
            .map(response -> AiChatResponse.builder()
                .answer(response.getResult().getOutput().getContent())
                .sessionId(request.getSessionId())
                .products(products)
                .build());
    }

    private List<AiChatProductCard> searchProductCards(String question) {
        List<Document> documents = vectorStore.similaritySearch(
            SearchRequest.query(question).withTopK(DEFAULT_PRODUCT_TOP_K)
        );
        return documents.stream()
            .map(this::toProductCard)
            .filter(Objects::nonNull)
            .toList();
    }

    private String buildProductContext(List<AiChatProductCard> products) {
        if (products == null || products.isEmpty()) {
            return "\n\n没有检索到相关商品信息。";
        }
        String productText = products.stream()
            .map(product -> String.format("- 商品:%s; 价格:%s; 描述:%s",
                product.getProductName(),
                product.getCurrentPrice() == null ? "未知" : product.getCurrentPrice().toPlainString(),
                product.getDescription() == null ? "" : product.getDescription()))
            .collect(Collectors.joining("\n"));
        return "\n\n商品信息如下:\n" + productText;
    }

    private AiChatProductCard toProductCard(Document document) {
        Map<String, Object> metadata = document.getMetadata();
        if (metadata == null || metadata.isEmpty()) {
            return null;
        }
        return AiChatProductCard.builder()
            .productId(parseLong(metadata.get("productId")))
            .productName(Objects.toString(metadata.get("productName"), null))
            .mainImage(Objects.toString(metadata.get("mainImage"), null))
            .currentPrice(parseBigDecimal(metadata.get("currentPrice")))
            .description(Objects.toString(metadata.get("description"), null))
            .detailUrl(Objects.toString(metadata.get("detailUrl"), null))
            .build();
    }

    private Long parseLong(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number number) {
            return number.longValue();
        }
        try {
            return Long.parseLong(value.toString());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private BigDecimal parseBigDecimal(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal bigDecimal) {
            return bigDecimal;
        }
        if (value instanceof Number number) {
            return BigDecimal.valueOf(number.doubleValue());
        }
        try {
            return new BigDecimal(value.toString());
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}

