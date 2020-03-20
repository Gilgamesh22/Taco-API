package taco.api

import io.micronaut.core.annotation.Introspected
import io.swagger.v3.oas.annotations.media.Schema

import java.math.BigDecimal;

class OrderTotal(price: BigDecimal, quantity: Int) {

    @Schema(description="The total price of the order")
    val price : BigDecimal = price;

    @Schema(description="The total number of items in the order")
    val quantity : Int = quantity;
}