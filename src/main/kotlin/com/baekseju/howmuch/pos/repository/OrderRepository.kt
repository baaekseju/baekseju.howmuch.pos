package com.baekseju.howmuch.pos.repository

import com.baekseju.howmuch.pos.entity.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository: JpaRepository<Order, Int> {
}