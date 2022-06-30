package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.dto.OrderDto
import com.baekseju.howmuch.pos.entity.Category
import com.baekseju.howmuch.pos.entity.Menu
import com.baekseju.howmuch.pos.entity.MenuOrderMap
import com.baekseju.howmuch.pos.entity.Order
import com.baekseju.howmuch.pos.repository.MenuOrderMapRepository
import com.baekseju.howmuch.pos.repository.MenuRepository
import com.baekseju.howmuch.pos.repository.OrderRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.Instant
import java.util.*

@SpringBootTest
internal class OrderServiceTest {
    @Autowired
    private lateinit var orderService: OrderService

    @MockBean
    private lateinit var orderRepository: OrderRepository

    @MockBean
    private lateinit var menuRepository: MenuRepository

    @MockBean
    private lateinit var menuOrderMapRepository: MenuOrderMapRepository

    private fun <T> any(): T {
        return BDDMockito.any()
    }

    @Test
    fun addOrder() {
        val orderId = 1
        var savedOrder: Order? = null
        given(orderRepository.save(any())).will {
            val order: Order = it.getArgument(0)
            savedOrder = Order(
                id = orderId,
                totalPrice = order.totalPrice,
                payWith = order.payWith,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
            savedOrder
        }
        val menu = Menu(
            id = 1,
            name = "싸이버거",
            category = Category(id = 1, name = "햄버거"),
            price = 5900,
            additionalPrice = 0,
            imageUrl = "https://test.com",
            stock = 999,
            hidden = false
        )
        given(menuRepository.findById(any())).willReturn(Optional.of(menu))
        given(menuOrderMapRepository.save(any())).willReturn(
            MenuOrderMap(
                id = 1,
                menu = menu,
                order = savedOrder,
                quantity = 1
            )
        )

        val mockOrderDto = OrderDto(
            menus = listOf(OrderDto.Menu(id = 1, quantity = 1)),
            totalPrice = 10000,
            payWith = "card"
        )
        val orderDto = orderService.addOrder(mockOrderDto)

        then(orderRepository).should().save(any())
        then(menuOrderMapRepository).should().save(any())
        assertThat(orderDto.id).isEqualTo(orderId)
        assertThat(orderDto.menus).hasSize(1)
    }
}
