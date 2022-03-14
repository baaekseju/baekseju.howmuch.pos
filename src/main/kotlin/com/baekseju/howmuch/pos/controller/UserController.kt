package com.baekseju.howmuch.pos.controller

import com.baekseju.howmuch.pos.dto.ResponseDto
import com.baekseju.howmuch.pos.dto.UserDto
import com.baekseju.howmuch.pos.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addUser(@RequestBody user: UserDto): ResponseDto {
        val userDto = userService.addUser(user)
        return ResponseDto(
            status = HttpStatus.CREATED.value(),
            message = "",
            data = userDto
        )
    }
}