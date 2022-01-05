package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.dto.SetMenuDto
import com.baekseju.howmuch.pos.mapper.SetMenuMapper
import com.baekseju.howmuch.pos.repository.SetMenuRepository
import org.springframework.stereotype.Service

@Service
class SetMenuService(val setMenuRepository: SetMenuRepository, val setMenuMapper: SetMenuMapper) {
    fun getSetMenus(hidden: Boolean): List<SetMenuDto> {
        val setMenus = setMenuRepository.findAllByHiddenAndDeletedAtIsNull(hidden)
        return setMenuMapper.toDtos(setMenus)
    }
}
