package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.dto.CategoryDto
import com.baekseju.howmuch.pos.mapper.CategoryMapper
import com.baekseju.howmuch.pos.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(val categoryRepository: CategoryRepository, val categoryMapper: CategoryMapper) {
    fun addCategory(categoryDto: CategoryDto): CategoryDto {
        val categoryEntity = categoryRepository.save(categoryMapper.toEntity(categoryDto))

        return categoryMapper.ToDto(categoryEntity)
    }
}