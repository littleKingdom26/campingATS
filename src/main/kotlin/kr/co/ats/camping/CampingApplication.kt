package kr.co.ats.camping

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing(auditorAwareRef="userAuditingConfig")
@SpringBootApplication
class CampingApplication

fun main(args: Array<String>) {
    runApplication<CampingApplication>(*args)
}
