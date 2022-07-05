package com.baekseju.howmuch.pos.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

@Entity
@EntityListeners(value = [AuditingEntityListener::class])
class Point(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    @field:NotNull
    @field:Positive
    var point: Int?,
    @field:NotNull
    @OneToOne
    var user: User?,
    @CreatedDate
    var createdAt: Instant? = null,
    @LastModifiedDate
    var updatedAt: Instant? = null,
    deletedAt: Instant? = null
) : SoftDeleteEntity(deletedAt) {
    fun addPoint(point: Int) {
        this.point = this.point?.plus(point)
    }
}
