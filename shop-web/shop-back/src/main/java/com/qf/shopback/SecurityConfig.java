package com.qf.shopback;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.service.IBackUserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * @version 1.0
 * @user 36043
 * @date 2019/7/4 18:52
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Reference
    private IBackUserService backUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().loginPage("/tologin")
                .loginProcessingUrl("/login")
                .failureUrl("/tologin?error=1")
                .permitAll()
            .and()
            .logout().permitAll()
            .and()
            .authorizeRequests()
                .mvcMatchers("/resources/**").permitAll()
                .mvcMatchers("/").authenticated()
                .anyRequest().access("@perssionHandler.hasPerssion(request,authentication)")
            .and()
            .csrf().disable()
            .headers().frameOptions().sameOrigin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(backUserService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
