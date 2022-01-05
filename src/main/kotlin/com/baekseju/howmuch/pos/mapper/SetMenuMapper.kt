package com.baekseju.howmuch.pos.mapper

import com.baekseju.howmuch.pos.dto.SetMenuDto
import com.baekseju.howmuch.pos.entity.SetMenu
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface SetMenuMapper {
    fun toDtos(setMenuDtos: List<SetMenu>): List<SetMenuDto>
}
