package com.baekseju.howmuch.pos.repository

import com.baekseju.howmuch.pos.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Int> {
    fun findByPhoneNumber(phoneNumber: String): User?
}