package taco.api

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Post
import io.micronaut.http.HttpResponse
import io.micronaut.validation.Validated

import javax.validation.constraints.NotBlank
import javax.validation.Valid

import java.math.RoundingMode;
import java.math.BigDecimal
import kotlin.collections.List

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Validated
@Controller("/menu-total") 
class OrderTotalController {

    @Post("/")
    fun index(@Body @Valid menuItems: List<MenuItem> ): HttpResponse<OrderTotal> {

        // calculate the price of the cart
        var totalPrice : BigDecimal = menuItems
                        .map { it.price().multiply(BigDecimal(it.quantity())) }
                        .reduce { total, price -> total.add(price) }

        // calculate number of items in cart
        var numItems : Int = menuItems
                        .map { it.quantity() }
                        .reduce { total, quantity -> total + quantity }

        // apply 20% discount if the number of times equals or grater then 4
        if (numItems >= 4) {
            totalPrice = totalPrice.multiply(BigDecimal("80"))
            totalPrice = totalPrice.divide(BigDecimal("100"), 4, RoundingMode.HALF_EVEN)
        }

        // force the precision to 2 decimal points
        totalPrice = totalPrice.setScale(2, RoundingMode.HALF_EVEN)

        // fill order total for response
        val orderTotal = OrderTotal(totalPrice, numItems)
        return HttpResponse.created(orderTotal)
    }
}