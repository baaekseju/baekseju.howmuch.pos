package com.baekseju.howmuch.pos.dto

import java.time.Instant
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

class UserDto(
    val id: Int? = null,
    @field:NotNull(message = "한 글자 이상 입력해야 합니다.")
    @field:Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "010-xxxx-xxxx 형식으로 입력해야 합니다.")
    var phoneNumber: String,
    var createdAt: Instant? = null,
    var updatedAt: Instant? = null,
    var deletedAt: Instant? = null
) : BaseDto
