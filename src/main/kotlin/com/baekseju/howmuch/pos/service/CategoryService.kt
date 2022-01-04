package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.dto.CategoryDto
import com.baekseju.howmuch.pos.mapper.CategoryMapper
import com.baekseju.howmuch.pos.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(val categoryRepository: CategoryRepository, val categoryMapper: CategoryMapper) {
    fun addCategory(categoryDto: CategoryDto): CategoryDto {
        val categoryEntity = categoryRepository.save(categoryMapper.toEntity(categoryDto))

        return categoryMapper.toDto(categoryEntity)
    }

    fun updateCategory(categoryId: Int, categoryDto: CategoryDto): CategoryDto{
        val categoryEntity = categoryRepository.findById(categoryId).get()
        categoryEntity.updateCategory(categoryDto)
        categoryRepository.save(categoryEntity)
        return categoryMapper.toDto(categoryEntity)
    }
    fun deleteCategory(categoryId: Int, force: Boolean): String {
        val categoryEntity = categoryRepository.findById(categoryId).get()
        return  if (force) {
            categoryRepository.delete(categoryEntity)
            "force delete success"
        } else {
            categoryEntity.softDelete()
            categoryRepository.save(categoryEntity)
            "soft delete success"
        }
    }
}