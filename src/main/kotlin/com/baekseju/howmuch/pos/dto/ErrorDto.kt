package com.baekseju.howmuch.pos.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Instant

@JsonInclude(JsonInclude.Include.NON_NULL)
class ErrorDto (
    val timeStamp: Instant = Instant.now(),
    val status: Int,
    val error: String,
    val path: String,
    val message: String?
)