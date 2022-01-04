package com.baekseju.howmuch.pos.mapper

import com.baekseju.howmuch.pos.dto.MenuDto
import com.baekseju.howmuch.pos.entity.Menu
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface MenuMapper {
    fun toEntity(menuDto: MenuDto): Menu
    fun toDto(menu: Menu): MenuDto
    fun toDtos(menus: List<Menu>): List<MenuDto>
}
