package com.example.backend.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import java.lang.Exception


@Configuration
class SecurityConfig: WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
       http.csrf().disable().httpBasic().disable().headers().frameOptions().disable();
    }
}