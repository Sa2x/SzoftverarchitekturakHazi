package com.example.backend.config

import com.example.backend.auth.AuthTokenWebResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@Configuration
class CustomConfig : WebMvcConfigurationSupport() {

    @Bean
    fun authWebArgumentResolverFactory(): HandlerMethodArgumentResolver {
        return AuthTokenWebResolver()
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()

    // Addding the AuthWebResolver to the default argument resolvers
    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.add(authWebArgumentResolverFactory())
    }

    // TODO: define CORS mappings
    public override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "DELETE", "PATCH", "POST", "PUT")
    }

}