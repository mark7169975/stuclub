package com.ccut.yiyi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: MyAuthenticationSuccessHandler
 * @Description: TODO
 * @Author: Mark
 * @Date: 2020/2/10 20:04
 * @version: V1.0
 */

@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 处理登录成功的
     *
     * @param request  请求
     * @param response 响应
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication
            authentication)
            throws IOException, ServletException {
        //什么都不做的话，那就直接调用父类的方法
       // super.onAuthenticationSuccess(request, response, authentication);

        Map<String, String> map = new HashMap<>();
        map.put("code", "200");
        map.put("msg", "登录成功");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(map));
        //跳转到某个页面
        new DefaultRedirectStrategy().sendRedirect(request, response, "/pages/index.html");
    }
}