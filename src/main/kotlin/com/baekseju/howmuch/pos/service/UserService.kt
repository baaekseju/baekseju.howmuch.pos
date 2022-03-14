package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.dto.UserDto
import com.baekseju.howmuch.pos.mapper.UserMapper
import com.baekseju.howmuch.pos.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository, val userMapper: UserMapper) {
    fun getUserByPhoneNumber(phoneNumber: String) : UserDto{
        val user = userRepository.findByPhoneNumber(phoneNumber)
        return userMapper.toDto(user)
    }
}
