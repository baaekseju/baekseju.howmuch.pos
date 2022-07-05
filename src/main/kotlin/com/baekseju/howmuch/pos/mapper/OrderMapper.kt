package com.baekseju.howmuch.pos.mapper

import com.baekseju.howmuch.pos.dto.OrderDto
import com.baekseju.howmuch.pos.entity.Order
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface OrderMapper {
//    fun toEntity(orderDto: OrderDto): Order
    fun toEntity(orderDto: OrderDto): Order
}
