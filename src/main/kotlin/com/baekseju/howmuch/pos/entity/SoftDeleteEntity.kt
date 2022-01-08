package com.baekseju.howmuch.pos.entity

import java.time.Instant
import javax.persistence.MappedSuperclass

@MappedSuperclass
class SoftDeleteEntity(var deletedAt: Instant?) {
    fun softDelete() {
        deletedAt = Instant.now()
    }
}
