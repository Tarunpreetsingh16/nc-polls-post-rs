package com.neighborhood.connect.pollspostsrs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@SpringBootApplication
@EnableWebSecurity
@EntityScan(basePackages = ["com.neighborhood.connect.*"])
@EnableJpaRepositories(basePackages = ["com.neighborhood.connect.*"])
@ComponentScan(basePackages = ["com.neighborhood.connect.*"])
class PollsPostsRsApplication

fun main(args: Array<String>) {
	runApplication<PollsPostsRsApplication>(*args)
}
