package dev.insidemind.bank.service

import java.math.BigDecimal
import javax.inject.Singleton

@Singleton
class CurrencyRatingService {
    fun getCurrentRating(): CurrencyRating {
        return CurrencyRating(BigDecimal(4.213123))

    }

}

data class CurrencyRating(val value: BigDecimal)
