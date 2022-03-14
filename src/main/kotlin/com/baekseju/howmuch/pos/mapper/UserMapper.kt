package com.baekseju.howmuch.pos.mapper

import com.baekseju.howmuch.pos.dto.UserDto
import com.baekseju.howmuch.pos.entity.User
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface UserMapper {
    fun toEntity(userDto: UserDto): User
    fun toDto(user: User): UserDto
}
