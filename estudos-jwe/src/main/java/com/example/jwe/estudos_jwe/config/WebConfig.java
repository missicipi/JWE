package com.example.jwe.estudos_jwe.config;

import com.example.jwe.estudos_jwe.security.SecurityHeadersInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final SecurityHeadersInterceptor securityHeadersInterceptor;

    public WebConfig(SecurityHeadersInterceptor securityHeadersInterceptor) {
        this.securityHeadersInterceptor = securityHeadersInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityHeadersInterceptor);
    }
}