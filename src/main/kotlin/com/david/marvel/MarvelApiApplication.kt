	package com.david.marvel

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MarvelApiApplication

fun main(args: Array<String>) {
	runApplication<MarvelApiApplication>(*args)
}
