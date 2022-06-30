package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.dto.OrderDto
import com.baekseju.howmuch.pos.entity.MenuOrderMap
import com.baekseju.howmuch.pos.entity.Order
import com.baekseju.howmuch.pos.exception.StockNotEnoughException
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
    val orderMapper: OrderMapper
) {
    @Transactional
    fun addOrder(order: OrderDto): OrderDto {
        val orderEntity = Order(
            totalPrice = order.totalPrice,
            payWith = order.payWith
        )
        val savedOrderEntity = orderRepository.save(orderEntity)

        val orderDtoMenuList: MutableList<OrderDto.Menu> = mutableListOf()

        order.menus?.map { menu ->
            val orderedMenu = menuRepository.findById(menu.id!!).orElse(null) ?: throw EntityNotFoundException()
            if (orderedMenu.stock!! < menu.quantity!!) throw StockNotEnoughException()
            orderedMenu.stock = orderedMenu.stock?.minus(menu.quantity)
            val menuOrderMap = MenuOrderMap(
                menu = orderedMenu,
                order = savedOrderEntity,
                quantity = menu.quantity
            )
            val savedMenuOrderMap = menuOrderMapRepository.save(menuOrderMap)
            orderDtoMenuList.add(orderMapper.toOrderDtoMenu(savedMenuOrderMap))
        }

        return OrderDto(
            id = savedOrderEntity.id,
            menus = orderDtoMenuList,
            totalPrice = savedOrderEntity.totalPrice,
            payWith = savedOrderEntity.payWith
        )
    }
}
