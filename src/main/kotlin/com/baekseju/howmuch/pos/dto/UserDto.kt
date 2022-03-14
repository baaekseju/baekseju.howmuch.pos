package com.baekseju.howmuch.pos.dto

import java.time.Instant

class UserDto(
    val id: Int? = null,
    var phoneNumber: String,
    var createdAt: Instant? = null,
    var updatedAt: Instant? = null,
    var deletedAt: Instant? = null
) : BaseDto {
}
