package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.dto.SetMenuDto
import com.baekseju.howmuch.pos.mapper.SetMenuMapper
import com.baekseju.howmuch.pos.repository.SetMenuRepository
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class SetMenuService(val setMenuRepository: SetMenuRepository, val setMenuMapper: SetMenuMapper) {
    fun getSetMenus(hidden: Boolean): List<SetMenuDto> {
        val setMenus = setMenuRepository.findAllByHiddenAndDeletedAtIsNull(hidden)
        return setMenuMapper.toDtos(setMenus)
    }

    fun getSetMenu(setMenuId: Int): SetMenuDto {
        val setMenu = setMenuRepository.findById(setMenuId).orElse(null) ?: throw EntityNotFoundException()
        return setMenuMapper.toDto(setMenu)
    }

    fun addMenu(setMenuDto: SetMenuDto): SetMenuDto {
        val setMenu = setMenuRepository.save(setMenuMapper.toEntity(setMenuDto))
        return setMenuMapper.toDto(setMenu)
    }
}
