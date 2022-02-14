package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.dto.CategoryDto
import com.baekseju.howmuch.pos.mapper.CategoryMapper
import com.baekseju.howmuch.pos.repository.CategoryRepository
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class CategoryService(val categoryRepository: CategoryRepository, val categoryMapper: CategoryMapper) {
    fun addCategory(categoryDto: CategoryDto): CategoryDto {
        val categoryEntity = categoryRepository.save(categoryMapper.toEntity(categoryDto))
        return categoryMapper.toDto(categoryEntity)
    }

    fun getCategories(): List<CategoryDto> {
        val categoryEntities = categoryRepository.findAll()
        return categoryMapper.toDtos(categoryEntities)
    }

    fun updateCategory(categoryId: Int, categoryDto: CategoryDto): CategoryDto {
        val categoryEntity = categoryRepository.findById(categoryId).orElse(null) ?: throw EntityNotFoundException()
        categoryEntity.updateCategory(categoryDto)
        categoryRepository.save(categoryEntity)
        return categoryMapper.toDto(categoryEntity)
    }

    fun deleteCategory(categoryId: Int): String {
        val categoryEntity = categoryRepository.findById(categoryId).orElse(null) ?: throw EntityNotFoundException()
        categoryRepository.delete(categoryEntity)
        return "force delete success"
    }
}
