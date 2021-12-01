package com.baekseju.howmuch.pos.mapper

import com.baekseju.howmuch.pos.dto.MenuDto
import com.baekseju.howmuch.pos.entity.Menu
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface MenuMapper {
    fun menuDtoToEntity(menuDto: MenuDto): Menu
    fun menuEntityToDto(menu: Menu): MenuDto

    // 함수 명이 맘에 안듬 위에 함수랑 너무 비슷
    fun menuEntitiesToDtos(menus: List<Menu>): List<MenuDto>
}
