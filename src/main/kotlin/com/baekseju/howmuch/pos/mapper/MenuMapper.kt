package com.baekseju.howmuch.pos.mapper

import com.baekseju.howmuch.pos.dto.MenuDto
import com.baekseju.howmuch.pos.entity.Menu
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface MenuMapper {
    fun menuDtoToEntity(menuDto: MenuDto): Menu
    fun menuEntityToDto(Menu: Menu): MenuDto
}