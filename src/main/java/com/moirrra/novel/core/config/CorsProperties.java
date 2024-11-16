package com.moirrra.novel.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-08
 * @Description: 跨域配置属性
 * @Version: 1.0
 */
@ConfigurationProperties(prefix = "novel.cors")
@Data
public class CorsProperties {
    /**
     * 允许跨域的域名
     */
    private List<String> allowOrigins;
}
