package com.baekseju.howmuch.pos.mapper

import com.baekseju.howmuch.pos.dto.PointDto
import com.baekseju.howmuch.pos.entity.Point
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface PointMapper {
    @Mapping(target = "user", ignore = true)
    fun toEntity(pointDto: PointDto): Point
    fun toDto(point: Point): PointDto
}
