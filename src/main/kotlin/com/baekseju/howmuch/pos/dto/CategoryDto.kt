package com.baekseju.howmuch.pos.dto

import java.time.Instant

class CategoryDto(

    val name: String,
    var createdAt: Instant? = null,
    var updatedAt: Instant? = null
)
