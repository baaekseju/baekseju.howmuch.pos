package com.baekseju.howmuch.pos

import com.baekseju.howmuch.pos.config.ApplicationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties::class)
class PosApplication

fun main(args: Array<String>) {
	runApplication<PosApplication>(*args)
}
