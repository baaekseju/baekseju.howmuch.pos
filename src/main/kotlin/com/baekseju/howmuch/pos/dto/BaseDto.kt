package com.baekseju.howmuch.pos.dto

import java.time.Instant

open class BaseDto(
    val createdAt: Instant?,
    val updatedAt: Instant?
)
