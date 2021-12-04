package com.baekseju.howmuch.pos.dto

import com.baekseju.howmuch.pos.entity.Menu
import com.fasterxml.jackson.annotation.JsonView
import java.time.Instant

class MenuDto(
    @JsonView(MenuJsonView.Simple::class)
    val id: Int? = null,
    @JsonView(MenuJsonView.Simple::class)
    var name: String,
    @JsonView(MenuJsonView.Simple::class)
    var price: Int,
    @JsonView(MenuJsonView.Simple::class)
    var additionalPrice: Int,
    @JsonView(MenuJsonView.Detail::class)
    var categoryId: Int,
    @JsonView(MenuJsonView.Simple::class)
    var stock: Int,
    @JsonView(MenuJsonView.Detail::class)
    var hidden: Boolean,
    @JsonView(MenuJsonView.Detail::class)
    createdAt: Instant? = null,
    @JsonView(MenuJsonView.Detail::class)
    updatedAt: Instant? = null,
    var deletedAt: Instant? = null
): BaseDto(createdAt, updatedAt)
