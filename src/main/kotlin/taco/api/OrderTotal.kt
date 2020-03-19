package taco.api

import io.micronaut.core.annotation.Introspected
import java.math.BigDecimal;

@Introspected
class OrderTotal(price: BigDecimal, quantity: Int) {

    protected val price : BigDecimal = price;

    protected val quantity : Int = quantity;
    
    fun price(): BigDecimal {
        return this.price
    }

    fun quantity(): Int {
        return this.quantity
    }
}