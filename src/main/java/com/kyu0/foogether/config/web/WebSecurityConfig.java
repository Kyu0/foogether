package com.kyu0.foogether.config.web;

import com.kyu0.foogether.config.provider.CustomAuthenticationProvider;
import com.kyu0.foogether.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final int STRENGTH = 10;
    private MemberService memberService;

    @Autowired
    public WebSecurityConfig(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .headers()
                .frameOptions().disable()
        .and()
            .csrf()
                .disable()
            .authorizeRequests()
                    .antMatchers("/login", "/register", "/forgot").permitAll()
                    .antMatchers("/css/**", "/js/**").permitAll()
                    .antMatchers("/").authenticated()
            .and()
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/api/v1/login")
            .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authManager) throws Exception {
        authManager.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider(memberService, getPasswordEncoder());
    }

    @Bean
    public static PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(STRENGTH);
    }
}
