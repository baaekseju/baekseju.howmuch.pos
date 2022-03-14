package com.baekseju.howmuch.pos.dto

class ResponseDto(
    val status: Int,
    val message: String,
    val data: BaseDto? = null
)
