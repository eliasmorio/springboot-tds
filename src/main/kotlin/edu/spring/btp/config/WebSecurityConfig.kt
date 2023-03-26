package edu.spring.btp.config

import edu.spring.btp.service.DbUserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true,
    jsr250Enabled = true
)
class WebSecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun configure(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests()
            .requestMatchers("/complaints/**").hasAuthority("USER")
            .anyRequest().permitAll()
            .and()
            .exceptionHandling().accessDeniedPage("/error403")
            .and()
            .formLogin().loginPage("/login").successForwardUrl("/login-success").failureForwardUrl("/login-error")
            .and()
            .logout().logoutUrl("/logout").logoutSuccessUrl("/logout-success")
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