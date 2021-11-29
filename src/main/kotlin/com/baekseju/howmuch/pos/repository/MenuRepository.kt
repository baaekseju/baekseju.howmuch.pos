package com.baekseju.howmuch.pos.repository

import com.baekseju.howmuch.pos.entity.Menu
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MenuRepository : JpaRepository<Menu, Int> {
    fun findAllByHiddenIsFalseAndDeleteAtIsNull(): List<Menu>

    fun findByIdAndHiddenIsFalseAndDeleteAtIsNull(id: Int): Optional<Menu>
}
