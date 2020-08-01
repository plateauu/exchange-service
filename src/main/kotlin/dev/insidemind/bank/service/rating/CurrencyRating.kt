package dev.insidemind.bank.service.rating

import dev.insidemind.bank.model.Amount
import java.math.BigDecimal

data class CurrencyRating(private val rating: BigDecimal) {

    fun exchange(amount: Amount): Amount = amount.multiply(Amount(rating))

}
