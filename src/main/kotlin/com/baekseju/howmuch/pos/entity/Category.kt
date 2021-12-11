package com.baekseju.howmuch.pos.entity

import java.time.Instant
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Category (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Int? = null,
    val name : String,
    val createdAt : Instant? = null,
    val updatedAt : Instant? = null
)

