package com.baekseju.howmuch.pos.dto

import java.time.Instant

class OrderDto(
    val id: Int? = null,
    var menus: String,
    var price: Int,
    var payWith: String,
    var createdAt: Instant? = null,
    var updatedAt: Instant? = null,
    var deletedAt: Instant? = null
) : BaseDto
