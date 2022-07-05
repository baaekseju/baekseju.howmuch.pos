package com.baekseju.howmuch.pos.repository

import com.baekseju.howmuch.pos.entity.MenuOrderMap
import org.springframework.data.jpa.repository.JpaRepository

interface MenuOrderMapRepository : JpaRepository<MenuOrderMap, Int> {
}
