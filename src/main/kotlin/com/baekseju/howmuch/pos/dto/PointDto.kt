package com.baekseju.howmuch.pos.dto

import java.time.Instant

class PointDto(
    val id: Int? = null,
    var point: Int,
    var createdAt: Instant? = null,
    var updatedAt: Instant? = null,
    var deletedAt: Instant? = null
): BaseDto
