package com.baekseju.howmuch.pos.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/categories/**")
            .allowedOrigins("http://localhost:3001", "http://127.0.0.1:3001", "http://localhost:3002", "http://127.0.0.1:3002")
        registry.addMapping("/api/orders/**")
            .allowedOrigins("http://localhost:3001", "http://127.0.0.1:3001")
        registry.addMapping("/api/users/**")
            .allowedOrigins("http://localhost:3001", "http://127.0.0.1:3001")
            .allowedMethods("*")
        registry.addMapping("/api/menus")
            .allowedOrigins("http://localhost:3002", "http://127.0.0.1:3002")
        registry.addMapping("/api/menus/*")
            .allowedOrigins("http://localhost:3002", "http://127.0.0.1:3002")
            .allowedMethods("*")
    }
}
