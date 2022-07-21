package com.baekseju.howmuch.pos.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        val posClientUrls = arrayOf(
            "http://localhost:3001",
            "http://127.0.0.1:3001",
            "http://192.168.1.18:9033",
            "https://rodemtree.synology.me:8003"
        )
        val posManagerUrls = arrayOf(
            "http://localhost:3002",
            "http://127.0.0.1:3002",
            "http://192.168.1.18:9022",
            "https://rodemtree.synology.me:8002"
        )

        registry.addMapping("/api/categories/**")
            .allowedOrigins(*posClientUrls, *posManagerUrls)
        registry.addMapping("/api/orders/**")
            .allowedOrigins(*posClientUrls)
        registry.addMapping("/api/users/**")
            .allowedOrigins(*posClientUrls)
            .allowedMethods("*")
        registry.addMapping("/api/menus")
            .allowedOrigins(*posManagerUrls)
        registry.addMapping("/api/menus/*")
            .allowedOrigins(*posManagerUrls)
            .allowedMethods("*")
    }
}
