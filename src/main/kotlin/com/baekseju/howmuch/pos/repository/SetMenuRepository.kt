package com.baekseju.howmuch.pos.repository

import com.baekseju.howmuch.pos.entity.SetMenu
import org.springframework.data.jpa.repository.JpaRepository

interface SetMenuRepository : JpaRepository<SetMenu, Int> {
    fun findAllByHiddenAndDeletedAtIsNull(hidden: Boolean): List<SetMenu>
}
