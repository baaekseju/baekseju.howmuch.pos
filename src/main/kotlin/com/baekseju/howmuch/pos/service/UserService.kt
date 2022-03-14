package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.dto.UserDto
import com.baekseju.howmuch.pos.exception.UserExistException
import com.baekseju.howmuch.pos.mapper.UserMapper
import com.baekseju.howmuch.pos.repository.UserRepository
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class UserService(val userRepository: UserRepository, val userMapper: UserMapper) {
    fun getUserByPhoneNumber(phoneNumber: String): UserDto {
        val user = userRepository.findByPhoneNumber(phoneNumber) ?: throw EntityNotFoundException()
        return userMapper.toDto(user)
    }

    fun addUser(userDto: UserDto): UserDto {
        val existUser = userRepository.findByPhoneNumber(userDto.phoneNumber)
        if (existUser != null) {
            throw UserExistException(userDto.phoneNumber)
        }
        val user = userRepository.save(userMapper.toEntity(userDto))
        return userMapper.toDto(user)
    }
}
