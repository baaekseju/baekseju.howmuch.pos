package com.baekseju.howmuch.pos.dto

import com.fasterxml.jackson.annotation.JsonView
import org.hibernate.validator.constraints.URL
import java.time.Instant
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class MenuDto(
    @JsonView(MenuJsonView.Simple::class)
    val id: Int? = null,
    @JsonView(MenuJsonView.Simple::class)
    @field:NotBlank(message = "한 글자 이상 입력해야 합니다.")
    var name: String?,
    @JsonView(MenuJsonView.Simple::class)
    @field:NotNull(message = "한 글자 이상 입력해야 합니다.")
    @field:Min(value = 0L, message = "0 이상 입력해야 합니다.")
    var price: Int?,
    @JsonView(MenuJsonView.Detail::class)
    @field:NotNull(message = "한 글자 이상 입력해야 합니다.")
    @field:URL(message = "URL 형식이어야 합니다.")
    var imageUrl: String?,
    @JsonView(MenuJsonView.Detail::class)
    @field:NotNull(message = "한 글자 이상 입력해야 합니다.")
    @field:Min(value = 0L, message = "0 이상 입력해야 합니다.")
    var additionalPrice: Int?,
    @JsonView(MenuJsonView.Detail::class)
    @field:NotNull(message = "값이 존재해야합니다.")
    var categoryId: Int?,
    @JsonView(MenuJsonView.Simple::class)
    @field:NotNull(message = "한 글자 이상 입력해야 합니다.")
    @field:Min(value = 0L, message = "0 이상 입력해야 합니다.")
    var stock: Int?,
    @JsonView(MenuJsonView.Detail::class)
    @field:NotNull(message = "값이 존재해야합니다.")
    var hidden: Boolean?,
    @JsonView(MenuJsonView.Detail::class)
    var createdAt: Instant? = null,
    @JsonView(MenuJsonView.Detail::class)
    var updatedAt: Instant? = null,
    var deletedAt: Instant? = null
)
