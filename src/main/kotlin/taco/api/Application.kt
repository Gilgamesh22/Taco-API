package taco.api

import io.micronaut.runtime.Micronaut
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.*
import io.swagger.v3.oas.annotations.info.*

@OpenAPIDefinition(
    info = Info(
        title = "Taco API",
        version = "1.0.1",
        description = "This API allows you to calculate and order total cost from a given set of menu items",
        license = License(name = "Apache 2.0", url = ""),
        contact = Contact(url = "", name = "Zaid", email = "ZNackasha@gmail.com")
    )
)

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("taco.api")
                .mainClass(Application.javaClass)
                .start()
    }
}