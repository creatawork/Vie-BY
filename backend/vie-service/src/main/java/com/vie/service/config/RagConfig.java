package com.vie.service.config;

import com.zaxxer.hikari.HikariDataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.sql.DataSource;
import org.springframework.ai.autoconfigure.vectorstore.pgvector.PgVectorStoreProperties;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiEmbeddingClient;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.retry.RetryUtils;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableConfigurationProperties(PgVectorStoreProperties.class)
public class RagConfig {

    private static final Logger log = LoggerFactory.getLogger(RagConfig.class);

    @Value("${spring.ai.openai.api-key:}")
    private String openAiApiKey;

    @Value("${spring.ai.openai.base-url:https://dashscope.aliyuncs.com/compatible-mode/v1}")
    private String openAiBaseUrl;

    @Value("${spring.ai.openai.chat.options.model:}")
    private String openAiChatModel;

    @Value("${spring.ai.openai.embedding.options.model:}")
    private String openAiEmbeddingModel;

    @Bean
    @Primary
    public OpenAiApi openAiApi() {
        String resolvedBaseUrl = resolveOpenAiBaseUrl();
        log.info("Resolved OpenAI base URL: {}", resolvedBaseUrl);
        log.info("Resolved OpenAI API key present: {}", StringUtils.hasText(openAiApiKey));
        log.info("Resolved OpenAI chat model: {}", openAiChatModel);
        log.info("Resolved OpenAI embedding model: {}", openAiEmbeddingModel);
        // Ensure the underlying RestClient always has an absolute base URL.
        // Otherwise, JDK HttpClient will reject relative URIs with: "URI with undefined scheme".
        RestClient.Builder resolvedBuilder = RestClient.builder()
                .baseUrl(resolvedBaseUrl)
                .requestInterceptor((request, body, execution) -> {
                    log.info("OpenAI request: {} {}", request.getMethod(), request.getURI());
                    return execution.execute(request, body);
                });
        OpenAiApi api = new OpenAiApi(resolvedBaseUrl, openAiApiKey, resolvedBuilder);
        log.info("Created OpenAiApi instance: {}@{}", api.getClass().getName(), Integer.toHexString(System.identityHashCode(api)));
        logOpenAiApiRestClient(api);
        return api;
    }

    @Bean
    @Primary
    public RestClient.Builder openAiRestClientBuilder() {
        return RestClient.builder().baseUrl(resolveOpenAiBaseUrl());
    }

    @Bean
    @Primary
    public ChatClient chatClient(OpenAiApi openAiApi) {
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .withModel(openAiChatModel)
                .build();
        ChatClient chatClient = new OpenAiChatClient(openAiApi, options);
        log.info(
                "Wired ChatClient implementation: {} with OpenAiApi {}@{} (model={})",
                chatClient.getClass().getName(),
                openAiApi.getClass().getName(),
                Integer.toHexString(System.identityHashCode(openAiApi)),
                options.getModel());
        return chatClient;
    }

    @Bean
    @Primary
    public EmbeddingClient embeddingClient(OpenAiApi openAiApi) {
        OpenAiEmbeddingOptions options = StringUtils.hasText(openAiEmbeddingModel)
                ? OpenAiEmbeddingOptions.builder().withModel(openAiEmbeddingModel).build()
                : OpenAiEmbeddingOptions.builder().build();
        EmbeddingClient embeddingClient = new OpenAiEmbeddingClient(
                openAiApi,
                MetadataMode.EMBED,
                options,
                RetryUtils.DEFAULT_RETRY_TEMPLATE);
        log.info(
                "Wired EmbeddingClient implementation: {} with OpenAiApi {}@{} (model={})",
                embeddingClient.getClass().getName(),
                openAiApi.getClass().getName(),
                Integer.toHexString(System.identityHashCode(openAiApi)),
                options.getModel());
        return embeddingClient;
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource dataSource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.ai.vectorstore.pgvector.datasource")
    public DataSourceProperties pgvectorDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource pgvectorDataSource(
            @Qualifier("pgvectorDataSourceProperties") DataSourceProperties pgvectorDataSourceProperties) {
        return pgvectorDataSourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @Primary
    public PgVectorStore pgVectorStore(
            EmbeddingClient embeddingClient,
            OpenAiApi openAiApi,
            PgVectorStoreProperties pgVectorStoreProperties,
            @Qualifier("pgvectorDataSource") DataSource pgvectorDataSource) {
        log.info(
                "PgVectorStore wiring EmbeddingClient: {} (OpenAiApi hash: {})",
                embeddingClient.getClass().getName(),
                Integer.toHexString(System.identityHashCode(openAiApi)));
        JdbcTemplate jdbcTemplate = new JdbcTemplate(pgvectorDataSource);
        return new PgVectorStore(
                jdbcTemplate,
                embeddingClient,
                pgVectorStoreProperties.getDimensions(),
                pgVectorStoreProperties.getDistanceType(),
                pgVectorStoreProperties.isRemoveExistingVectorStoreTable(),
                pgVectorStoreProperties.getIndexType());
    }

    private void logOpenAiApiRestClient(OpenAiApi api) {
        try {
            Field restClientField = OpenAiApi.class.getDeclaredField("restClient");
            restClientField.setAccessible(true);
            Object restClient = restClientField.get(api);
            log.info(
                    "OpenAiApi restClient instance: {}@{}",
                    restClient.getClass().getName(),
                    Integer.toHexString(System.identityHashCode(restClient)));
            logRestClientBaseUrl(restClient);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            log.warn("Failed to introspect OpenAiApi restClient", ex);
        }
    }



    private void logRestClientBaseUrl(Object restClient) {
        try {
            Method baseUrlMethod = restClient.getClass().getDeclaredMethod("baseUrl");
            baseUrlMethod.setAccessible(true);
            Object baseUrl = baseUrlMethod.invoke(restClient);
            log.info("OpenAiApi restClient baseUrl: {}", baseUrl);
            return;
        } catch (ReflectiveOperationException ignored) {
            // Fall through to reflection-based field access.
        }

        try {
            Field baseUrlField = restClient.getClass().getDeclaredField("baseUrl");
            baseUrlField.setAccessible(true);
            Object baseUrl = baseUrlField.get(restClient);
            log.info("OpenAiApi restClient baseUrl (field): {}", baseUrl);
            return;
        } catch (ReflectiveOperationException ignored) {
            // Fall through to field scan.
        }

        Field[] fields = restClient.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(restClient);
                if (field.getName().toLowerCase().contains("base")
                        || field.getName().toLowerCase().contains("uri")) {
                    log.info(
                            "RestClient field {} ({}) = {}",
                            field.getName(),
                            field.getType().getName(),
                            value);
                    if (value != null && field.getName().toLowerCase().contains("uribuilder")) {
                        logUriBuilderFactory(value);
                    }
                }
            } catch (IllegalAccessException ex) {
                log.warn("Failed to read RestClient field {}", field.getName(), ex);
            }
        }
    }

    private void logUriBuilderFactory(Object uriBuilderFactory) {
        try {
            Field baseUrlField = uriBuilderFactory.getClass().getDeclaredField("baseUrl");
            baseUrlField.setAccessible(true);
            Object baseUrl = baseUrlField.get(uriBuilderFactory);
            log.info("UriBuilderFactory baseUrl: {}", baseUrl);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
            // Fall back to scanning fields.
        }

        Field[] fields = uriBuilderFactory.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(uriBuilderFactory);
                if (field.getName().toLowerCase().contains("base")
                        || field.getName().toLowerCase().contains("uri")) {
                    log.info(
                            "UriBuilderFactory field {} ({}) = {}",
                            field.getName(),
                            field.getType().getName(),
                            value);
                    if (value != null && field.getName().toLowerCase().contains("baseuri")) {
                        logBaseUriDetails(value);
                    }
                }
            } catch (IllegalAccessException ex) {
                log.warn("Failed to read UriBuilderFactory field {}", field.getName(), ex);
            }
        }
    }

    private void logBaseUriDetails(Object baseUriBuilder) {
        try {
            Method toUriString = baseUriBuilder.getClass().getDeclaredMethod("toUriString");
            toUriString.setAccessible(true);
            Object uriString = toUriString.invoke(baseUriBuilder);
            log.info("UriComponentsBuilder toUriString: {}", uriString);
        } catch (ReflectiveOperationException ex) {
            log.warn("Failed to read UriComponentsBuilder.toUriString", ex);
        }

        Field[] fields = baseUriBuilder.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(baseUriBuilder);
                if (field.getName().toLowerCase().contains("scheme")
                        || field.getName().toLowerCase().contains("host")
                        || field.getName().toLowerCase().contains("uri")
                        || field.getName().toLowerCase().contains("path")) {
                    log.info(
                            "UriComponentsBuilder field {} ({}) = {}",
                            field.getName(),
                            field.getType().getName(),
                            value);
                }
            } catch (IllegalAccessException ex) {
                log.warn("Failed to read UriComponentsBuilder field {}", field.getName(), ex);
            }
        }
    }


    private String resolveOpenAiBaseUrl() {
        String resolvedBaseUrl = StringUtils.hasText(openAiBaseUrl)
                ? openAiBaseUrl
                : "https://dashscope.aliyuncs.com/compatible-mode/v1";
        if (!resolvedBaseUrl.startsWith("http://") && !resolvedBaseUrl.startsWith("https://")) {
            resolvedBaseUrl = "https://" + resolvedBaseUrl;
        }
        if (resolvedBaseUrl.endsWith("/v1")) {
            resolvedBaseUrl = resolvedBaseUrl.substring(0, resolvedBaseUrl.length() - 3);
        }
        return resolvedBaseUrl;
    }
}


