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
    @GetMapping
    fun getCategories(): List<CategoryDto> {
        return categoryService.getCategories()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCategory(@RequestBody category: CategoryDto): CategoryDto{
        return categoryService.addCategory(category)
    }

    @PutMapping("/{categoryId}")
    // http://127.0.0.1:8000/api/categories/1
    //    {
    //        "name" : "burger"
    //    }
    fun putCategory(@PathVariable categoryId: Int, @RequestBody category: CategoryDto): CategoryDto{
        return categoryService.updateCategory(categoryId, category)
    }

    @DeleteMapping("/{categoryId}")
    // http://127.0.0.1:8000/api/categories/1?force=true
    fun deleteCategory(@PathVariable categoryId: Int, @RequestParam(defaultValue = "false") force: Boolean): String{
        return  categoryService.deleteCategory(categoryId, force)
    }
}
