package com.baekseju.howmuch.pos.controller

import com.baekseju.howmuch.pos.dto.CategoryDto
import com.baekseju.howmuch.pos.service.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/categories")
class CategoryController(
    val categoryService: CategoryService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCategory(@RequestBody category: CategoryDto): CategoryDto{
        return categoryService.addCategory(category)
    }
}