package com.test.memory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Configuration
//@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter {
    
    /**
     * 重写该方法，设定用户访问权限
     * 用户身份可以访问 订单相关API
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers("/test/**").hasRole("USER")    //用户权限
        .antMatchers("/users/**").hasRole("ADMIN")    //管理员权限
        .and()
        .formLogin()
        .loginPage("/login")    //跳转登录页面的控制器，该地址要保证和表单提交的地址一致！
        .successHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1, Authentication arg2)
                    throws IOException, ServletException {
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (principal != null && principal instanceof UserDetails) {
                    UserDetails user = (UserDetails) principal;
                    System.out.println("loginUser:"+user.getUsername());
                    //维护在session中
                    arg0.getSession().setAttribute("userDetail", user);
                    arg1.sendRedirect("/test/welcome");
                } 
            }
        })
        .permitAll()
        .and()
        .logout()
        .permitAll()
        .and()
        .csrf().disable();        //暂时禁用CSRF，否则无法提交表单
    }

    /**
     * 重写该方法，添加自定义用户
     * 用户名，密码，角色信息放在内存中
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
        .withUser("admin").password("admin").roles("ADMIN","USER")
        .and()
        .withUser("terry").password("terry").roles("USER")
        .and()
        .withUser("larry").password("larry").roles("USER");
    }

    /**
     * 加密/验证器
     * spring有多种实现：如MD5,SHA-1,SHA-256...
     * 也可以自己实现PasswordEncoder接口
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
    
}