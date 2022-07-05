package com.baekseju.howmuch.pos.mapper

import com.baekseju.howmuch.pos.dto.MenuDto
import com.baekseju.howmuch.pos.entity.Menu
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface MenuMapper {
    @Mapping(target = "category", ignore = true)
    fun toEntity(menuDto: MenuDto): Menu

    @Mapping(source = "category.id", target = "categoryId")
    fun toDto(menu: Menu): MenuDto
    fun toDtos(menus: List<Menu>): List<MenuDto>
}
