package taco.api

import io.micronaut.core.annotation.Introspected

import java.math.BigDecimal;

import javax.validation.constraints.Digits
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.*

import io.swagger.v3.oas.annotations.media.Schema

//@Introspected
@Schema(name="MenuItem", description="Menu Item in shopping cart")
open class MenuItem {

    @NotBlank
    @NotEmpty(message = "Please enter name")
    @Schema(description="The name of the menu item")
    val name : String = "";

    @NotNull(message = "Please enter price")
    @Schema(description="The price of the menu item")
    @Digits(integer=10, fraction=2)
    val price : BigDecimal = BigDecimal("0.0");

    @NotNull(message = "Please enter the quantity")
    @Schema(description="The quantity the user would like to purchase")
    val quantity : Int = 0;
}