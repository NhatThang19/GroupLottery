package com._chaumedia.GroupLottery.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com._chaumedia.GroupLottery.service.RoleService;
import com._chaumedia.GroupLottery.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserController {
    private final RoleService roleService;
    private final UserService userService;
    private static final String PATH = "user";

    @GetMapping("")
    public String getUserPage(Model model) {
        model.addAttribute("path", PATH);
        return "admin/user/show";
    }

}
