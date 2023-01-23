package com.key.oa

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity

@SpringBootApplication
@EnableMethodSecurity
class KeyOABackendApplication

fun main(args: Array<String>) {
    runApplication<KeyOABackendApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}
