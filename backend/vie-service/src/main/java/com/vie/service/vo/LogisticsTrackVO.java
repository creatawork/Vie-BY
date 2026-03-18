package com.vie.service.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 物流轨迹VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsTrackVO {

    /**
     * 轨迹时间
     */
    private String time;

    /**
     * 状态
     */
    private String status;

    /**
     * 描述
     */
    private String desc;
}
