package com.baekseju.howmuch.pos.dto

import java.time.LocalDateTime

open class BaseDto(
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)