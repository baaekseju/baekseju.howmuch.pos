package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.dto.OrderDto
import com.baekseju.howmuch.pos.entity.Order
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

@SpringBootTest
internal class OrderServiceTest {
    @Autowired
    private lateinit var orderService: OrderService

    @MockBean
    private lateinit var orderRepository: OrderRepository

    private fun <T> any(): T {
        return BDDMockito.any()
    }

    @Test
    fun addOrder(){
        val orderId = 1
        given(orderRepository.save(any())).will {
            val order: Order = it.getArgument(0)
            Order(
                id = orderId,
                menus = order.menus,
                price = order.price,
                payWith = order.payWith,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        }
        val mockOrderDto = OrderDto(
            menus = "{주문내역}",
            price = 10000,
            payWith = "card"
        )

        val orderDto = orderService.addOrder(mockOrderDto)

        then(orderRepository).should().save(any())
        assertThat(orderDto.id).isEqualTo(orderId)
    }
}
