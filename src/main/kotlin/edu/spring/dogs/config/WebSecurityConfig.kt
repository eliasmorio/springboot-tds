package edu.spring.dogs.config

import edu.spring.dogs.services.DbUserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebSecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun configure(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests()
            .requestMatchers("/init/**", "/h2-console/**", "/login/**").permitAll()
            .requestMatchers("/master/**").hasRole("manager")
            .anyRequest().authenticated()     // Doit arriver après les requestMatchers
            .and()
            .formLogin().loginPage("/login").successForwardUrl("/")
            .and()
            .headers().frameOptions().sameOrigin()
            .and()
            .csrf().disable()

        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService? {
        return DbUserService()
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder? {
        return BCryptPasswordEncoder()
    }

}