package com.baekseju.howmuch.pos.entity

import com.baekseju.howmuch.pos.dto.MenuDto
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import javax.persistence.*

@Entity
@EntityListeners(value = [AuditingEntityListener::class])
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
    @CreatedDate
    var createdAt: Instant? = null,
    @LastModifiedDate
    var updatedAt: Instant? = null,
    deletedAt: Instant? = null
): SoftDeleteEntity(deletedAt){
    fun updateMenu(menuDto: MenuDto){
        name = menuDto.name
        price = menuDto.price
        additionalPrice = menuDto.additionalPrice
        categoryId = menuDto.categoryId
        stock = menuDto.stock
        hidden = menuDto.hidden
    }
}
