package com.digihome.library.api

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import java.text.SimpleDateFormat
import java.util.*

@SpringBootApplication
@EnableJpaRepositories
class ApiApplication {

	val logger = LoggerFactory.getLogger(this::class.java)

	@Bean
	fun mapper() : ObjectMapper {
		return ObjectMapper().apply {
			configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
			configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true)
			configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
			registerModule(KotlinModule())
			registerModule(JavaTimeModule())
			dateFormat = SimpleDateFormat("yyyy-MM-dd")
			setTimeZone(TimeZone.getTimeZone("America/Toronto"))
		}
	}
}

fun main(args: Array<String>) {
	runApplication<ApiApplication>(*args)
}