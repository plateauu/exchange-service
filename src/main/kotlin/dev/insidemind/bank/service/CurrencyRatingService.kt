package dev.insidemind.bank.service

import dev.insidemind.bank.model.Amount
import dev.insidemind.bank.utils.annotation.Mockable
import java.math.BigDecimal
import javax.inject.Singleton

@Mockable
@Singleton
class CurrencyRatingService {

    fun getCurrentPlnToUsdRating(): CurrencyRating {
        return CurrencyRating(BigDecimal(0.23))

    }

    fun getCurrentUsdToPlnRating(): CurrencyRating {
        return CurrencyRating(BigDecimal(3.75))
    }

}

data class CurrencyRating(private val rating: BigDecimal) {

    fun exchange(amount: Amount): Amount = amount.multiply(Amount(rating))

}
