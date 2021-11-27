package com.baekseju.howmuch.pos.entity

import com.baekseju.howmuch.pos.dto.MenuDto
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Menu(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    var name: String,
    var price: Int,
    var additionalPrice: Int,
    var categoryId: Int,
    var stock: Int,
    var hidden: Boolean,
    createdAt: LocalDateTime? = null,
    updatedAt: LocalDateTime? = null,
    deletedAt: LocalDateTime? = null
): SoftDeleteEntity(createdAt, updatedAt, deletedAt){
    fun toDto(): MenuDto {
        return MenuDto(
            id = id,
            name = name,
            price = price,
            additionalPrice = additionalPrice,
            categoryId = categoryId,
            stock = stock,
            hidden = hidden,
            createdAt = createdAt,
            updatedAt = updatedAt,
            deletedAt = deleteAt
        )
    }
}