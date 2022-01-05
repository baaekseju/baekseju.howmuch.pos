package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.dto.SetMenuDto
import com.baekseju.howmuch.pos.mapper.SetMenuMapper
import com.baekseju.howmuch.pos.repository.SetMenuRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Instant
import javax.persistence.EntityNotFoundException

@Service
class SetMenuService(val setMenuRepository: SetMenuRepository, val setMenuMapper: SetMenuMapper) {
    fun getSetMenus(hidden: Boolean): List<SetMenuDto> {
        val setMenus = setMenuRepository.findAllByHiddenAndDeletedAtIsNull(hidden)
        return setMenuMapper.toDtos(setMenus)
    }

    fun getSetMenu(setMenuId: Int): SetMenuDto {
        val setMenu = setMenuRepository.findById(setMenuId).orElse(null) ?: throw EntityNotFoundException("setmenu not exist")
        return setMenuMapper.toDto(setMenu)
    }
}
