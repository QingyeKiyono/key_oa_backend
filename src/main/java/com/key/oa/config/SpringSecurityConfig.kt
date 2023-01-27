package com.key.oa.config

import com.key.oa.filter.JwtAuthenticationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SpringSecurityConfig @Autowired constructor(
    val jwtAuthenticationFilter: JwtAuthenticationFilter,
    val userDetailsService: UserDetailsService
) {
    @Bean
    fun authenticationManager(configuration: AuthenticationConfiguration): AuthenticationManager {
        return configuration.authenticationManager
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        // 覆盖掉原有的logout接口，否则会自动重定向而无法正常完成功能
        http.logout().logoutUrl("/defaultLogout")
        http.authorizeHttpRequests().requestMatchers("defaultLogout")

        http
            .csrf().disable()  // 取消csrf限制，之后可能会引起安全问题
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 不通过Session获取SecurityContext
            .and().authorizeHttpRequests()
            .requestMatchers("/login").anonymous()  // 登录接口允许匿名访问
            .anyRequest().authenticated()  // 其余接口都需要登录后访问

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider? {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordEncoder())
        provider.setUserDetailsService(userDetailsService)

        // 关键在这里，否则无法区分是否是'登录用户不存在'还是‘密码错误’
        provider.isHideUserNotFoundExceptions = false
        return provider
    }
}