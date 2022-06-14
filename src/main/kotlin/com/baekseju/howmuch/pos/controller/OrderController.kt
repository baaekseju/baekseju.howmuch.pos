package com.baekseju.howmuch.pos.controller

import com.baekseju.howmuch.pos.dto.OrderDto
import com.baekseju.howmuch.pos.dto.ResponseDto
import com.baekseju.howmuch.pos.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:3001", "http://127.0.0.1:3001"])
@RestController
@RequestMapping("/api/orders")
class OrderController(
    val orderService: OrderService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addOrder(@RequestBody order: OrderDto): ResponseDto {
        val orderDto = orderService.addOrder(order)
        return ResponseDto(HttpStatus.CREATED.value(), "주문 완료", orderDto)
    }
}
