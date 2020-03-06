
package com.ccut.yiyi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

/**
 * @ClassName: SecurityConfig 安全证证配置类
 * @Description: TODO
 * @Author: Mark
 * @Date: 2019/12/25 14:43
 * @version: V1.0
 */

@Configuration
@EnableWebSecurity
@Component
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationProvider provider;  //注入我们自己的AuthenticationProvider
    @Autowired
    private MyAuthenticationFailHander myAuthenticationFailHander;
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login.html")//表单登录页面
                .loginProcessingUrl("/login/form")//提交登录页面的url
                .successHandler(myAuthenticationSuccessHandler)//登录成功
                .failureHandler(myAuthenticationFailHander)//登录失败
                .permitAll()//permitAll()表示上面这些不需要验证
                .and()
                .authorizeRequests().antMatchers("/css/**", "/img/**", "/js/**",
                "/plugins/**","/queryErrorMess","/assets/**","/pages/ui/**","/ui/**")
                .permitAll()//设置以上资源可以访问
                .and()
                .authorizeRequests()
                .antMatchers("/**").hasAnyRole("ADMIN","SUPERADMIN")
                .anyRequest().authenticated()//除了上面的设置所有访问都需要权限
                .and()
                .csrf().disable();//关闭csrf跨域
        http.headers().frameOptions().sameOrigin();//因为此系统使用了框架页，所有要设置框架页策略为sameOrigin
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //设置登录信息
        auth.authenticationProvider(provider);
    }
}

