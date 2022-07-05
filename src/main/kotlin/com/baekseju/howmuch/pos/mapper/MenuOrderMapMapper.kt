package com.baekseju.howmuch.pos.mapper

import com.baekseju.howmuch.pos.dto.OrderDto
import com.baekseju.howmuch.pos.entity.MenuOrderMap
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface MenuOrderMapMapper {
//    fun toEntity(orderDto: OrderDto): Order
    @Mapping(source = "menu.id", target = "id")
    fun toMenuItem(menuOrderMap: MenuOrderMap): OrderDto.MenuItem
}
