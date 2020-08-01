package dev.insidemind.bank

import io.micronaut.runtime.Micronaut.build
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
    info = Info(
            title = "bank",
            version = "0.0"
    )
)
object Api {
}

fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("dev.insidemind.bank")
		.start()
}

