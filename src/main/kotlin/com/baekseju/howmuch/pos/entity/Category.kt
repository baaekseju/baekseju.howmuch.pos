package com.baekseju.howmuch.pos.entity

import com.baekseju.howmuch.pos.dto.CategoryDto
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import javax.persistence.*

@Entity
@EntityListeners(value = [AuditingEntityListener::class])
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    var name: String?,
    @CreatedDate
    var createdAt: Instant? = null,
    @LastModifiedDate
    var updatedAt: Instant? = null
) {
    fun updateCategory(categoryDto: CategoryDto) {
        name = categoryDto.name
    }
}
