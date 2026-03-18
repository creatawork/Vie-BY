package com.vie.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 上传结果VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadResultVO {

    /**
     * 文件URL
     */
    private String url;
}
