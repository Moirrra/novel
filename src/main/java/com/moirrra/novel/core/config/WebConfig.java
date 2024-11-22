package com.moirrra.novel.core.config;

import com.moirrra.novel.core.interceptor.TokenParserInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Moirrra
 * @CreateTime: 2024-11-22
 * @Description:
 * @Version: 1.0
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final TokenParserInterceptor tokenParserInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenParserInterceptor);
    }
}
