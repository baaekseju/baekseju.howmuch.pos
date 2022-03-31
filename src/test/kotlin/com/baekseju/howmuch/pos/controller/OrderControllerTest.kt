package com.baekseju.howmuch.pos.controller

import com.baekseju.howmuch.pos.dto.OrderDto
import com.baekseju.howmuch.pos.service.OrderService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter
import java.time.Instant

@WebMvcTest(OrderController::class)
internal class OrderControllerTest {

    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var ctx: WebApplicationContext

    @MockBean
    private lateinit var orderService: OrderService

    private fun <T> any(): T {
        return BDDMockito.any()
    }

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
            .addFilter<DefaultMockMvcBuilder?>(CharacterEncodingFilter("UTF-8", true))
            .build()
    }

    @Test
    fun addOrder() {
        val orderId = 1
        given(orderService.addOrder(any())).will {
            val orderDto: OrderDto = it.getArgument(0)
            OrderDto(
                id = orderId,
                menus = orderDto.menus,
                price = orderDto.price,
                payWith = orderDto.payWith,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        }

        mockMvc.perform(
            post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"menus\": \"{주문내역...}\", \"price\": 10000, \"payWith\": \"card\"}")
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()))
            .andExpect(jsonPath("$.message").value("주문 완료"))
            .andExpect(jsonPath("$.data.id").value(orderId))
            .andDo(print())

        then(orderService).should().addOrder(any())
    }
}
