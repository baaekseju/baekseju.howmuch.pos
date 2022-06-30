package com.baekseju.howmuch.pos.dto

import java.time.Instant
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class OrderDto(
    val id: Int? = null,
    @field:NotNull
    var menus: List<Menu>?,
    @field:NotNull(message = "한 글자 이상 입력해야 합니다.")
    @field:Min(value = 0L, message = "0 이상 입력해야 합니다.")
    var totalPrice: Int?,
    @field:NotBlank(message = "한 글자 이상 입력해야 합니다.")
    var payWith: String?,
    var createdAt: Instant? = null,
    var updatedAt: Instant? = null,
    var deletedAt: Instant? = null
) : BaseDto {
    class Menu(
        @field:NotNull
        val id: Int?,
        @field:NotNull
        val quantity: Int?
    )
}
