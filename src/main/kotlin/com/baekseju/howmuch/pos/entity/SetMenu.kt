package com.baekseju.howmuch.pos.entity

import com.baekseju.howmuch.pos.dto.SetMenuDto
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import javax.persistence.*

@Entity
@EntityListeners(value = [AuditingEntityListener::class])
class SetMenu(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    var name: String,
    var price: Int,
    var imageUrl: String,
    var hidden: Boolean,
    @CreatedDate
    var createdAt: Instant? = null,
    @LastModifiedDate
    var updatedAt: Instant? = null,
    deletedAt: Instant? = null
) : SoftDeleteEntity(deletedAt) {
    fun updateSetMenu(setMenuDto: SetMenuDto) {
        name = setMenuDto.name
        price = setMenuDto.price
        imageUrl = setMenuDto.imageUrl
        hidden = setMenuDto.hidden
    }

}
