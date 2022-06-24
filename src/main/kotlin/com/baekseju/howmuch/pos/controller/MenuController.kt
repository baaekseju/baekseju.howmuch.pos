package com.baekseju.howmuch.pos.controller

import com.baekseju.howmuch.pos.dto.MenuDto
import com.baekseju.howmuch.pos.dto.MenuJsonView
import com.baekseju.howmuch.pos.service.MenuService
import com.fasterxml.jackson.annotation.JsonView
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/menus")
class MenuController(
    val menuService: MenuService
) {

    @GetMapping
    @JsonView(MenuJsonView.Simple::class)
    fun getMenus(@RequestParam(defaultValue = "false") hidden: Boolean): List<MenuDto> {
        return menuService.getMenus(hidden)
    }

    @GetMapping("/{menuId}")
    @JsonView(MenuJsonView.Detail::class)
    fun getMenu(@PathVariable menuId: Int): MenuDto {
        return menuService.getMenu(menuId)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addMenu(@Valid @RequestBody menu: MenuDto): MenuDto {
        return menuService.addMenu(menu)
    }

    @PutMapping("/{menuId}")
    fun putMenu(@PathVariable menuId: Int, @Valid @RequestBody menu: MenuDto): MenuDto {
        return menuService.updateMenu(menuId, menu)
    }

    @DeleteMapping("/{menuId}")
    fun deleteMenu(@PathVariable menuId: Int, @RequestParam(defaultValue = "false") force: Boolean): String {
        return menuService.deleteMenu(menuId, force)
    }
}
