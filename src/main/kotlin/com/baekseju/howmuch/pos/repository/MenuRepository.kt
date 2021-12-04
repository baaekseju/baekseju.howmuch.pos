package com.baekseju.howmuch.pos.repository

import com.baekseju.howmuch.pos.entity.Menu
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MenuRepository : JpaRepository<Menu, Int> {
    fun findAllByHiddenAndDeletedAtIsNull(hidden: Boolean): List<Menu>

    fun findByIdAndHiddenAndDeletedAtIsNull(id: Int, hidden: Boolean): Optional<Menu>
}
