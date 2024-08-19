package com.otl.accommodation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 파일이 저장된 경로를 URL로 매핑
        registry.addResourceHandler("/images/accommodation/**", "/images/room/**")
                .addResourceLocations("file:///C:/otl/images/accommodation/", "file:///C:/otl/images/room/");
    }
}
