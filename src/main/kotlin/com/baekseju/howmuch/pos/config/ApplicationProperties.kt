package com.baekseju.howmuch.pos.config

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Properties specific to Howmuch.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
class ApplicationProperties {
    // Application 설정 파일을 명시합니다.
}
