package com.baekseju.howmuch.pos.dto

import com.fasterxml.jackson.annotation.JsonView
import java.time.Instant

class CategoryDto(

    val name: String,
    var createdAt: Instant? = null,
    var updatedAt: Instant? = null

)