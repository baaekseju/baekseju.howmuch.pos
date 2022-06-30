package com.baekseju.howmuch.pos.mapper

import com.baekseju.howmuch.pos.dto.OrderDto
import com.baekseju.howmuch.pos.entity.MenuOrderMap
import com.baekseju.howmuch.pos.entity.Order
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface OrderMapper {
//    fun toEntity(orderDto: OrderDto): Order
    @Mapping(source = "menu.id", target = "id")
    fun toOrderDtoMenu(menuOrderMap: MenuOrderMap): OrderDto.Menu
}
