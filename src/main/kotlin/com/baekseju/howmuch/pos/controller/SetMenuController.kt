package com.baekseju.howmuch.pos.controller

import com.baekseju.howmuch.pos.dto.SetMenuDto
import com.baekseju.howmuch.pos.service.SetMenuService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/setmenus")
class SetMenuController(val setMenuService: SetMenuService) {

    @GetMapping
    fun getSetMenus(@RequestParam(defaultValue = "false") hidden: Boolean): List<SetMenuDto> {
        return setMenuService.getSetMenus(hidden)
    }
}
