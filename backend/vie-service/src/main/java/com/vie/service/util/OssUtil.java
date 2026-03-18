package com.vie.service.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.vie.service.config.OssConfig;
import com.vie.service.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 阿里云OSS工具类
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OssUtil {

    private final OSS ossClient;
    private final OssConfig ossConfig;

    /**
     * 允许的文件类型
     */
    private static final String[] ALLOWED_TYPES = {"image/jpeg", "image/png", "image/gif", "image/webp"};

    /**
     * 上传文件到OSS
     *
     * @param file 文件
     * @param folder 文件夹名称（如：avatar）
     * @return OSS文件URL
     */
    public String uploadFile(MultipartFile file, String folder) {
        // 验证文件
        validateFile(file);

        try {
            // 生成唯一的文件名
            String fileName = generateFileName(file.getOriginalFilename());
            String objectName = folder + "/" + fileName;

            // 上传文件到OSS
            InputStream inputStream = file.getInputStream();
            PutObjectRequest putObjectRequest = new PutObjectRequest(ossConfig.getBucketName(), objectName, inputStream);
            PutObjectResult result = ossClient.putObject(putObjectRequest);

            if (result != null) {
                // 返回文件URL
                return buildFileUrl(objectName);
            } else {
                throw new BusinessException("文件上传失败");
            }
        } catch (IOException e) {
            log.error("文件上传异常", e);
            throw new BusinessException("文件上传异常：" + e.getMessage());
        }
    }

    /**
     * 验证文件
     */
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("文件不能为空");
        }

        // 检查文件大小
        if (file.getSize() > ossConfig.getMaxFileSize()) {
            throw new BusinessException("文件大小不能超过 " + (ossConfig.getMaxFileSize() / 1024 / 1024) + "MB");
        }

        // 检查文件类型
        String contentType = file.getContentType();
        boolean isAllowed = false;
        for (String allowedType : ALLOWED_TYPES) {
            if (allowedType.equals(contentType)) {
                isAllowed = true;
                break;
            }
        }
        if (!isAllowed) {
            throw new BusinessException("不支持的文件类型，仅支持 jpg、png、gif、webp 格式");
        }
    }

    /**
     * 生成唯一的文件名
     */
    private String generateFileName(String originalFileName) {
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return UUID.randomUUID().toString() + extension;
    }

    /**
     * 构建文件URL
     */
    private String buildFileUrl(String objectName) {
        String endpoint = ossConfig.getEndpoint();
        // 移除 https:// 或 http:// 前缀
        if (endpoint.startsWith("https://")) {
            endpoint = endpoint.substring(8);
        } else if (endpoint.startsWith("http://")) {
            endpoint = endpoint.substring(7);
        }
        return "https://" + ossConfig.getBucketName() + "." + endpoint + "/" + objectName;
    }

    /**
     * 删除OSS文件
     *
     * @param objectName 对象名称
     */
    public void deleteFile(String objectName) {
        try {
            ossClient.deleteObject(ossConfig.getBucketName(), objectName);
        } catch (Exception e) {
            log.error("删除文件异常", e);
        }
    }
}
