package com.baekseju.howmuch.pos.dto

import java.time.Instant
import javax.validation.constraints.NotBlank

class CategoryDto(
    val id: Int? = null,
    @field:NotBlank(message = "한 글자 이상 입력해야 합니다.")
    val name: String?,
    var createdAt: Instant? = null,
    var updatedAt: Instant? = null
)
