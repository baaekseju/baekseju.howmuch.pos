package com.baekseju.howmuch.pos.dto

import com.baekseju.howmuch.pos.entity.Menu
import java.time.LocalDateTime

class MenuDto(
    val id: Int? = null,
    var name: String,
    var price: Int,
    var additionalPrice: Int,
    var categoryId: Int,
    var stock: Int,
    var hidden: Boolean,
    createdAt: LocalDateTime? = null,
    updatedAt: LocalDateTime? = null,
    var deletedAt: LocalDateTime? = null
): BaseDto(createdAt, updatedAt) {
    fun toEntity(): Menu {
        return Menu(
            name = name,
            price = price,
            additionalPrice = additionalPrice,
            categoryId = additionalPrice,
            stock = stock,
            hidden = hidden
        )
    }
}