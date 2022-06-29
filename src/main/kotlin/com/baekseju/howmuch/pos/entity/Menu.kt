package com.baekseju.howmuch.pos.entity

import com.baekseju.howmuch.pos.dto.MenuDto
import org.hibernate.validator.constraints.URL
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
@EntityListeners(value = [AuditingEntityListener::class])
class Menu(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    @field:NotBlank
    var name: String?,
    @field:NotNull
    @field:Min(value = 0L)
    var price: Int?,
    @field:NotEmpty
    @field:URL
    var imageUrl: String?,
    @field:NotNull
    @field:Min(value = 0L)
    var additionalPrice: Int?,
    @field:NotNull
    @ManyToOne
    var category: Category?,
    @field:NotNull
    @field:Min(value = 0L)
    var stock: Int?,
    @field:NotNull
    var hidden: Boolean?,
    @CreatedDate
    var createdAt: Instant? = null,
    @LastModifiedDate
    var updatedAt: Instant? = null,
    deletedAt: Instant? = null
) : SoftDeleteEntity(deletedAt) {
    fun updateMenu(menuDto: MenuDto, category: Category) {
        this.name = menuDto.name!!
        this.price = menuDto.price!!
        this.imageUrl = menuDto.imageUrl!!
        this.additionalPrice = menuDto.additionalPrice!!
        this.category = category
        this.stock = menuDto.stock!!
        this.hidden = menuDto.hidden!!
    }
}
