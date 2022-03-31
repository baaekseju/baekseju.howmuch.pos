package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.dto.OrderDto
import com.baekseju.howmuch.pos.mapper.OrderMapper
import com.baekseju.howmuch.pos.repository.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderService(val orderRepository: OrderRepository, val orderMapper: OrderMapper) {
    fun addOrder(order: OrderDto): OrderDto{
        val orderEntity = orderRepository.save(orderMapper.toEntity(order))
        return orderMapper.toDto(orderEntity)
    }
}
