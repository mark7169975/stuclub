package com.ccut.yiyi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @ClassName: SecurityConfig 安全证证配置类
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/25 14:43
 * @version: V1.0
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

      /*  http.authorizeRequests()
                .antMatchers("/login").permitAll() // 登录页面不拦截
                .antMatchers(HttpMethod.POST, "/login/form").permitAll().anyRequest().authenticated()// 对于登录路径不进行拦截
        .and().formLogin()// 配置登录页面
                .loginPage("/login");// 登录页面的访问路径;*/

       /* http.formLogin().loginPage("/login").loginProcessingUrl("/login/form")
                .permitAll()  //表单登录，permitAll()表示这个不需要验证 登录页面，登录失败页面
                .and()
                .authorizeRequests().antMatchers("/static/css/**", "/static/img/**", "/static/js/**",
                "/static/plugins/**")
                .permitAll()//设置静态资源可以访问
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .csrf().disable();*/

    }
}
