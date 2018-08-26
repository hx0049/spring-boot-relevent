package com.test.browser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BrowerSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    MyAuthenctiationSuccessHandler myAuthenctiationSuccessHandler;
    @Autowired
    MyAuthenctiationFailureHandler myAuthenctiationFailureHandler;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()                    //  定义当需要用户登录时候，转到的登录页面。
                .loginPage("/login")           // 设置登录页面
                .loginProcessingUrl("/user/login")  // 自定义的登录接口
                .successHandler(myAuthenctiationSuccessHandler) // 自定义登录成功处理
                .failureHandler(myAuthenctiationFailureHandler) // 自定义登录失败处理
                .and()
                .authorizeRequests()        // 定义哪些URL需要被保护、哪些不需要被保护
                .antMatchers("/login").permitAll()     // 设置所有人都可以访问登录页面
                .anyRequest()               // 任何请求,登录后可以访问
                .authenticated()
                .and()
                .csrf().disable();          // 关闭csrf防护
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
