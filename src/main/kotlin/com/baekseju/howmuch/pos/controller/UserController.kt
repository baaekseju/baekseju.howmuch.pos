package com.baekseju.howmuch.pos.controller

import com.baekseju.howmuch.pos.dto.PointDto
import com.baekseju.howmuch.pos.dto.ResponseDto
import com.baekseju.howmuch.pos.dto.UserDto
import com.baekseju.howmuch.pos.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Pattern

@RestController
@Validated
@RequestMapping("/api/users")
class UserController(val userService: UserService) {

    @GetMapping
    fun getUserByPhoneNumber(
        @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "010-xxxx-xxxx 형식으로 입력해야 합니다.")
        @RequestParam("phone-number")
        phoneNumber: String
    ): ResponseDto {
        val userDto = userService.getUserByPhoneNumber(phoneNumber)
        return ResponseDto(
            status = HttpStatus.OK.value(),
            message = "",
            data = userDto
        )
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addUser(@Valid @RequestBody user: UserDto): ResponseDto {
        val userDto = userService.addUser(user)
        return ResponseDto(
            status = HttpStatus.CREATED.value(),
            message = "",
            data = userDto
        )
    }

    @GetMapping("/{userId}/point")
    fun getPointByUser(@PathVariable userId: Int): ResponseDto {
        val pointDto = userService.getPointByUser(userId)
        return ResponseDto(
            status = HttpStatus.OK.value(),
            message = "",
            data = pointDto
        )
    }

    @PatchMapping("/{userId}/point")
    fun patchPointByUser(@PathVariable userId: Int, @Valid @RequestBody point: PointDto): ResponseDto {
        val pointDto = userService.addPoint(userId, point.point!!)
        return ResponseDto(
            status = HttpStatus.OK.value(),
            message = "",
            data = pointDto
        )
    }
}
