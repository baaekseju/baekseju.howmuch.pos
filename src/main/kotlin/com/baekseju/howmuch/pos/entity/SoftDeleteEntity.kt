package com.baekseju.howmuch.pos.entity

import java.time.LocalDateTime
import javax.persistence.MappedSuperclass

@MappedSuperclass
class SoftDeleteEntity(
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime,
    var deleteAt: LocalDateTime?,
) : BaseTimeEntity(createdAt, updatedAt) {
    fun softDelete(){
        deleteAt = LocalDateTime.now()
    }
}