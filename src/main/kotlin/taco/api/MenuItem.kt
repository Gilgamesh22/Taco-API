package taco.api

import io.micronaut.core.annotation.Introspected

import java.math.BigDecimal;

import javax.validation.constraints.Digits
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull


@Introspected
class MenuItem {

    @NotBlank
    @NotEmpty(message = "Please enter name")
    protected val name : String? = null;

    @Digits(integer=10, fraction=2)
    @NotNull(message = "Please enter price")
    protected val price : BigDecimal? = null;

    @NotNull(message = "Please enter the quantity")
    protected val quantity : Int? = null;

    fun name(): String {
        return this.name!!
    }

    fun price(): BigDecimal {
        return this.price!!
    }

    fun quantity(): Int {
        return this.quantity!!
    }
}