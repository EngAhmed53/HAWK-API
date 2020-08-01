package com.shouman.hawkAPI

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class HawkApiApplication

fun main(args: Array<String>) {
	runApplication<HawkApiApplication>(*args)
}
