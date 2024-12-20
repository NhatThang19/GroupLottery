package com._chaumedia.GroupLottery.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client")
public class AuthController {

    @GetMapping("/auth/login")
    public String login() {
        return "client/auth/login";
    }
}
