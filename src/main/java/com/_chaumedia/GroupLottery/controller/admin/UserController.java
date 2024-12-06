package com._chaumedia.GroupLottery.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com._chaumedia.GroupLottery.service.RoleService;

@Controller
@RequestMapping("/admin/user")
public class UserController {
    private final RoleService roleService;

    public UserController(RoleService roleService) {
        this.roleService = roleService;
    }

    String path = "user";

    @GetMapping("")
    public String getUserPage(Model model) {
        model.addAttribute("path", path);
        return "admin/user/show";
    }

}
