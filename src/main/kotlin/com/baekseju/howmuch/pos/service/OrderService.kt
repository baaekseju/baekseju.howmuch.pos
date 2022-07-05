package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.dto.OrderDto
import com.baekseju.howmuch.pos.entity.MenuOrderMap
import com.baekseju.howmuch.pos.exception.StockNotEnoughException
import com.baekseju.howmuch.pos.mapper.MenuOrderMapMapper
import com.baekseju.howmuch.pos.mapper.OrderMapper
import com.baekseju.howmuch.pos.repository.MenuOrderMapRepository
import com.baekseju.howmuch.pos.repository.MenuRepository
import com.baekseju.howmuch.pos.repository.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Service
class OrderService(
    val orderRepository: OrderRepository,
    val menuRepository: MenuRepository,
    val menuOrderMapRepository: MenuOrderMapRepository,
    val orderMapper: OrderMapper,
    val menuOrderMapMapper: MenuOrderMapMapper
) {
    @Transactional
    fun addOrder(order: OrderDto): OrderDto {
        val orderEntity = orderMapper.toEntity(order)
        val savedOrderEntity = orderRepository.save(orderEntity)

        val menuItems: MutableList<OrderDto.MenuItem> = mutableListOf()

        order.menuItems?.map { menu ->
            val orderedMenu = menuRepository.findById(menu.id!!).orElse(null) ?: throw EntityNotFoundException()
            if (orderedMenu.stock!! < menu.quantity!!) throw StockNotEnoughException()
            orderedMenu.stock = orderedMenu.stock?.minus(menu.quantity)
            val menuOrderMap = MenuOrderMap(
                menu = orderedMenu,
                order = savedOrderEntity,
                quantity = menu.quantity
            )
            val savedMenuOrderMap = menuOrderMapRepository.save(menuOrderMap)
            val menuItem = menuOrderMapMapper.toMenuItem(savedMenuOrderMap)
            menuItems.add(menuItem)
        }

        return OrderDto(
            id = savedOrderEntity.id,
            menuItems = menuItems,
            totalPrice = savedOrderEntity.totalPrice,
            payWith = savedOrderEntity.payWith
        )
    }
}
