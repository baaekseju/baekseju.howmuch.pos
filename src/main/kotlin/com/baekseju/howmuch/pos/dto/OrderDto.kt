package com.baekseju.howmuch.pos.dto

import java.time.Instant
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class OrderDto(
    val id: Int? = null,
    @field:NotBlank(message = "한 글자 이상 입력해야 합니다.")
    var menus: String?,
    @field:NotNull(message = "한 글자 이상 입력해야 합니다.")
    @field:Min(value = 0L, message = "0 이상 입력해야 합니다.")
    var price: Int?,
    @field:NotBlank(message = "한 글자 이상 입력해야 합니다.")
    var payWith: String?,
    var createdAt: Instant? = null,
    var updatedAt: Instant? = null,
    var deletedAt: Instant? = null
) : BaseDto
