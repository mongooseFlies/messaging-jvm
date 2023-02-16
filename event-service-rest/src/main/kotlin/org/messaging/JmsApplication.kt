package org.messaging

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JmsApplication

fun main(args: Array<String>) {
    runApplication<JmsApplication>(*args)
}
