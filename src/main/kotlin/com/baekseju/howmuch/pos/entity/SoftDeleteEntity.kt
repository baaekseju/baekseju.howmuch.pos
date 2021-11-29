package com.baekseju.howmuch.pos.entity

import java.time.Instant
import java.time.LocalDateTime
import javax.persistence.MappedSuperclass

@MappedSuperclass
class SoftDeleteEntity(var deleteAt: Instant?) {
    fun softDelete(){
        deleteAt = Instant.now()
    }
}
