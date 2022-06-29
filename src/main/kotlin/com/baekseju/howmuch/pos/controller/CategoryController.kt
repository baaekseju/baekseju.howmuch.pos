package com.baekseju.howmuch.pos.controller

import com.baekseju.howmuch.pos.dto.CategoryDto
import com.baekseju.howmuch.pos.dto.MenuDto
import com.baekseju.howmuch.pos.service.CategoryService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/categories")
class CategoryController(
    val categoryService: CategoryService
) {
    @GetMapping
    fun getCategories(): List<CategoryDto> {
        return categoryService.getCategories()
    }

    @GetMapping("/{categoryId}/menus")
    fun getMenusByCategory(@PathVariable categoryId: Int, pageable: Pageable): Page<MenuDto> {
        return categoryService.getMenusByCategory(categoryId, pageable)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCategory(@Valid @RequestBody category: CategoryDto): CategoryDto {
        return categoryService.addCategory(category)
    }

    @PutMapping("/{categoryId}")
    fun putCategory(@PathVariable categoryId: Int, @Valid @RequestBody category: CategoryDto): CategoryDto {
        return categoryService.updateCategory(categoryId, category)
    }

    @DeleteMapping("/{categoryId}")
    fun deleteCategory(@PathVariable categoryId: Int): String {
        return categoryService.deleteCategory(categoryId)
    }
}
