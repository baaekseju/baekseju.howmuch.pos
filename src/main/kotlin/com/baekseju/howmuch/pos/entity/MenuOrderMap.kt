package com.baekseju.howmuch.pos.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.validation.constraints.NotNull

@Entity
class MenuOrderMap(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    @NotNull
    @ManyToOne
    val menu: Menu? = null,
    @NotNull
    @ManyToOne
    val order: Order? = null,
    @field:NotNull
    val quantity: Int?
)
