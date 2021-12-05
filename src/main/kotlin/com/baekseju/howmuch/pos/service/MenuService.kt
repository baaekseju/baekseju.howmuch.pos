package com.baekseju.howmuch.pos.service

import com.baekseju.howmuch.pos.dto.MenuDto
import com.baekseju.howmuch.pos.mapper.MenuMapper
import com.baekseju.howmuch.pos.repository.MenuRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class MenuService(val menuRepository: MenuRepository, val menuMapper: MenuMapper) {

    fun getMenus(hidden: Boolean): List<MenuDto> {
        val menuEntities = menuRepository.findAllByHiddenAndDeletedAtIsNull(hidden)
        return menuMapper.menuEntitiesToDtos(menuEntities)
    }

    fun getMenuDetail(menuId: Int, hidden: Boolean): MenuDto {
        val menuEntity = menuRepository.findByIdAndHiddenAndDeletedAtIsNull(menuId, hidden) ?: throw EntityNotFoundException()
        return menuMapper.menuEntityToDto(menuEntity)
    }

    fun addMenu(menuDto: MenuDto): MenuDto {
        val menuEntity = menuRepository.save(menuMapper.menuDtoToEntity(menuDto))
        return menuMapper.menuEntityToDto(menuEntity)
    }

    fun updateMenu(menuId: Int, menuDto: MenuDto): MenuDto {
        val menuEntity = menuRepository.findById(menuId).orElseThrow{EntityNotFoundException()}
        menuEntity.updateMenu(menuDto)
        menuRepository.save(menuEntity)
        return menuMapper.menuEntityToDto(menuEntity)
    }

    fun deleteMenu(menuId: Int, force: Boolean): String {
        val menuEntity = menuRepository.findById(menuId).orElseThrow{EntityNotFoundException()}
        return if (force) {
            menuRepository.delete(menuEntity)
            "force delete success"
        } else {
            menuEntity.softDelete()
            menuRepository.save(menuEntity)
            "soft delete success"
        }
    }
}
