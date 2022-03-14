package com.baekseju.howmuch.pos.repository

import com.baekseju.howmuch.pos.entity.Point
import org.springframework.data.jpa.repository.JpaRepository

interface PointRepository: JpaRepository<Point, Int> {
    fun findByUserId(userId: Int): Point?
}
