package com.baekseju.howmuch.pos.mapper

import com.baekseju.howmuch.pos.dto.SetMenuDto
import com.baekseju.howmuch.pos.entity.SetMenu
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface SetMenuMapper {
    fun toDtos(setMenus: List<SetMenu>): List<SetMenuDto>
    fun toDto(setMenu: SetMenu): SetMenuDto
    fun toEntity(setMenuDto: SetMenuDto): SetMenu
}
