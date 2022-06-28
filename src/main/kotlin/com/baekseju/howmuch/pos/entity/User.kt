package com.baekseju.howmuch.pos.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

@Entity
@EntityListeners(value = [AuditingEntityListener::class])
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    @field:NotNull(message = "한 글자 이상 입력해야 합니다.")
    @field:Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "010-xxxx-xxxx 형식으로 입력해야 합니다.")
    var phoneNumber: String?,
    @CreatedDate
    var createdAt: Instant? = null,
    @LastModifiedDate
    var updatedAt: Instant? = null,
    deletedAt: Instant? = null
) : SoftDeleteEntity(deletedAt)
