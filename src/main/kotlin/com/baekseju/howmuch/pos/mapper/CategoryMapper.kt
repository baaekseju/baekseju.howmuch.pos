package com.baekseju.howmuch.pos.mapper

import com.baekseju.howmuch.pos.dto.CategoryDto
import com.baekseju.howmuch.pos.entity.Category
import org.mapstruct.Mapper


@Mapper(componentModel = "spring")
interface CategoryMapper {
    fun toEntity(categoryDto: CategoryDto): Category
    fun ToDto(category: Category): CategoryDto
}