package com.baekseju.howmuch.pos.entity

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Menu(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    var name: String,
    var price: Int,
    var additional_price: Int,
    var category_id: Int,
    var stock: Int,
    var hidden: Boolean,
    createdAt: LocalDateTime,
    updatedAt: LocalDateTime,
    deletedAt: LocalDateTime?
): SoftDeleteEntity(createdAt, updatedAt, deletedAt)