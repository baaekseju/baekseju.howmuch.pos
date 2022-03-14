package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.dto.PointDto
import com.baekseju.howmuch.pos.dto.UserDto
import com.baekseju.howmuch.pos.entity.Point
import com.baekseju.howmuch.pos.exception.UserExistException
import com.baekseju.howmuch.pos.mapper.PointMapper
import com.baekseju.howmuch.pos.mapper.UserMapper
import com.baekseju.howmuch.pos.repository.PointRepository
import com.baekseju.howmuch.pos.repository.UserRepository
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class UserService(
    val userRepository: UserRepository,
    val pointRepository: PointRepository,
    val userMapper: UserMapper,
    val pointMapper: PointMapper
) {
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
        pointRepository.save(
            Point(
                point = 0,
                user = user
            )
        )
        return userMapper.toDto(user)
    }

    fun getPointByUser(userId: Int): PointDto {
        val point = pointRepository.findByUserId(userId) ?: throw EntityNotFoundException()
        return pointMapper.toDto(point)
    }

    fun addPoint(userId: Int, pointDto: PointDto): PointDto {
        return PointDto(
            id = 1,
            point = 1500
        )
    }
}
