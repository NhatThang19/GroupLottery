package com._chaumedia.GroupLottery.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    String path= "dashboard";

    @GetMapping("/admin")
    public String getDashboardPage(Model model){
        model.addAttribute("path", path);
        return "admin/dashboard/show";
    }
}
