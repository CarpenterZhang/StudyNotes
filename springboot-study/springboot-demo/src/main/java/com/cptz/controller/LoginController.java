package com.cptz.controller;

import com.cptz.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(User user, HttpSession session) {
        session.setAttribute("sessionId", session.getId());
        return "redirect:dashboard";
    }

    @RequestMapping("dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
