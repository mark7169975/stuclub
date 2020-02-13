package com.ccut.yiyi.controller;

import com.ccut.yiyi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: LoginController
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/25 15:10
 * @version: V1.0
 */
@Controller
public class LoginController {
    @Autowired
    private StudentService studentService;

    /**
     * 查询登录人的信息
     */
    @GetMapping("/loginName")
    @ResponseBody
    public Map loginName(HttpServletRequest request) {
        //获取登录失败信息，然后从session中删除
        request.getSession().removeAttribute("ErrorMess");
        //获取登录人的账号信息
        String stuCode = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, String> map = new HashMap<>();
        map.put("loginStuCode", stuCode);
        map.put("loginStuName", studentService.findByStuCode(stuCode).getStuName());
        return map;
    }

    /**
     * 查询登录 失败的错误信息
     */
    @GetMapping("/queryErrorMess")
    @ResponseBody
    public Map queryErrorMess(HttpServletRequest request) {
        //获取登录失败信息
        String errorMess = (String) request.getSession().getAttribute("ErrorMess");
        Map<String, String> map = new HashMap<>();
        map.put("ErrorMessage", errorMess);
        return map;
    }
}
