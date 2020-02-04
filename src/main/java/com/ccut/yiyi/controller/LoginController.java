package com.ccut.yiyi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: LoginController
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/25 15:10
 * @version: V1.0
 */

@Controller

public class LoginController {
    @RequestMapping("/login")
    public String userLogin() {
        return "login";
    }

    @PostMapping("/login/form")
    public String loginForm() {
        System.out.println("1233312312");
        return "qq";
    }
}
