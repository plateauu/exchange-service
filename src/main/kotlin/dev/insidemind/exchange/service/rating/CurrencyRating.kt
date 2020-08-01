package dev.insidemind.exchange.service.rating

import dev.insidemind.exchange.model.Amount
import java.math.BigDecimal

data class CurrencyRating(private val rating: BigDecimal) {

    fun exchange(amount: Amount): Amount = amount.multiply(Amount(rating))

}
