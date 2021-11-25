package com.baekseju.howmuch.pos.dto

import com.baekseju.howmuch.pos.entity.Menu
import java.time.LocalDateTime

class MenuDto(
    val id: Int? = null,
    var name: String,
    var price: Int,
    var additional_price: Int,
    var category_id: Int,
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
            additional_price = additional_price,
            category_id = category_id,
            stock = stock,
            hidden = hidden
        )
    }
}