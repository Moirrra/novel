package com.moirrra.novel.core.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-16
 * @Description: OpenAPI配置
 * @Version: 1.0
 */
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Novel接口文档")
                        .description("Novel接口文档")
                        .version("v1")
                );
    }
}
