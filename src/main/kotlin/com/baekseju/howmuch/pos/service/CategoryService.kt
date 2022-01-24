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

    fun getCategories(): List<CategoryDto>{
        val categoryEntities = categoryRepository.findAll()

        return categoryMapper.toDtos(categoryEntities)
    }

    fun updateCategory(categoryId: Int, categoryDto: CategoryDto): CategoryDto{
        // categoryId = 1
        // categoryDto.name = "햄버거"
        // categoryEntity.name = "버거"
        val categoryEntity = categoryRepository.findById(categoryId).get()//있는지 없는지 판별하기위해
//        categoryEntity.name = categoryDto.name
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
