package com.baekseju.howmuch.pos.entity

import javax.persistence.*

@Entity
class MenuSetMenuMap(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    @ManyToOne
    val menu: Menu? = null,
    @ManyToOne
    val setMenu: SetMenu? = null,
    val numberOfMenu: Int
)
