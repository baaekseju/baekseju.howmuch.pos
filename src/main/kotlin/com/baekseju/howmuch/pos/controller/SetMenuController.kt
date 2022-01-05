package com.baekseju.howmuch.pos.controller

import com.baekseju.howmuch.pos.dto.SetMenuDto
import com.baekseju.howmuch.pos.service.SetMenuService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/setmenus")
class SetMenuController(val setMenuService: SetMenuService) {

    @GetMapping
    fun getSetMenus(@RequestParam(defaultValue = "false") hidden: Boolean): List<SetMenuDto> {
        return setMenuService.getSetMenus(hidden)
    }

    @GetMapping("/{setMenuId}")
    fun getSetMenu(@PathVariable setMenuId: Int): SetMenuDto {
        return setMenuService.getSetMenu(setMenuId)
    }
}
