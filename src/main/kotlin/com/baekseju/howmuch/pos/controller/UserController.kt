package com.baekseju.howmuch.pos.controller

import com.baekseju.howmuch.pos.dto.ResponseDto
import com.baekseju.howmuch.pos.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(val userService: UserService) {

    @GetMapping
    fun getUserByPhoneNumber(@RequestParam phoneNumber: String): ResponseDto {
        val userDto = userService.getUserByPhoneNumber(phoneNumber)
        return ResponseDto(
            status = HttpStatus.OK.value(),
            message = "",
            data = userDto
        )
    }
}