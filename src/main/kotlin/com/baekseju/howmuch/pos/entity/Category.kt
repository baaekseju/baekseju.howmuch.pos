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
    var name : String,
    var createdAt : Instant,
    var updatedAt : Instant
)

