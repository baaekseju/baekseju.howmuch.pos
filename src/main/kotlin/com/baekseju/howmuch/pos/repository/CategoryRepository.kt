package com.baekseju.howmuch.pos.repository

import com.baekseju.howmuch.pos.entity.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Int> {

}