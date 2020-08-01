package dev.insidemind.bank.service.rating

import dev.insidemind.bank.utils.annotation.Mockable
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Singleton

@Mockable
@Singleton
class CurrencyRatingService(
        private val nbpClient: NbpApiClient
) {
    private val logger: Logger = LoggerFactory.getLogger(CurrencyRatingService::class.java)

    fun getCurrentPlnToUsdRating(): CurrencyRating =
            getRating {
                BigDecimal.ONE.divide(it, 4, RoundingMode.HALF_UP)
            }

    fun getCurrentUsdToPlnRating(): CurrencyRating = getRating { it }

    private fun getRating(transform: (BigDecimal) -> BigDecimal): CurrencyRating {
        val (_, rates) = nbpClient.requestForUsdRating()
        val rating = BigDecimal(rates.first().ask)

        val currentRating = transform(rating)

        logger.info("Current rating USD to PLN: $rating")
        return CurrencyRating(currentRating)
    }
}
