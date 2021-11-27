package com.baekseju.howmuch.pos.controller

import com.baekseju.howmuch.pos.dto.MenuDto
import com.baekseju.howmuch.pos.service.MenuService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/menus")
class MenuController(
    val menuService: MenuService
) {

    @GetMapping
    fun getMenuList(): List<MenuDto> {
        return menuService.getMenuList()
    }

    @PostMapping
    fun addMenu(@RequestBody menu: MenuDto): ResponseEntity<MenuDto> {
        val menuDto = menuService.addMenu(menu)
        val uri = URI("/api/menus/" + menuDto.id);
        return ResponseEntity.created(uri).body(menuDto)
    }
}