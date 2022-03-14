package com.baekseju.howmuch.pos.mapper

import com.baekseju.howmuch.pos.dto.PointDto
import com.baekseju.howmuch.pos.entity.Point
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface PointMapper {
    fun toEntity(pointDto: PointDto): Point
    fun toDto(point: Point): PointDto
}
