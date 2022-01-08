package com.baekseju.howmuch.pos.dto

import java.time.Instant

class SetMenuDto(
    val id: Int? = null,
    var name: String,
    var price: Int,
    var imageUrl: String,
    var hidden: Boolean,
    var createdAt: Instant? = null,
    var updatedAt: Instant? = null,
    var deletedAt: Instant? = null
)
