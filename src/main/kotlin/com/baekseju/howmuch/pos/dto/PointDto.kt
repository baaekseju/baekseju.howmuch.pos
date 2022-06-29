package com.baekseju.howmuch.pos.dto

import java.time.Instant
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

class PointDto(
    val id: Int? = null,
    @field:NotNull(message = "한 글자 이상 입력해야 합니다.")
    @field:Positive(message = "1 이상 입력해야 합니다.")
    var point: Int?,
    var createdAt: Instant? = null,
    var updatedAt: Instant? = null,
    var deletedAt: Instant? = null
): BaseDto
