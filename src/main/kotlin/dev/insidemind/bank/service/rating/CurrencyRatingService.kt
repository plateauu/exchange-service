package dev.insidemind.bank.service.rating

import dev.insidemind.bank.utils.annotation.Mockable
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import javax.inject.Singleton

@Mockable
@Singleton
class CurrencyRatingService(
        private val nbpClient: NbpApiClient
) {
    private val logger: Logger = LoggerFactory.getLogger(CurrencyRatingService::class.java)

    //TODO Implement
    fun getCurrentPlnToUsdRating(): CurrencyRating {
        val (_, rates) = nbpClient.requestForUsdRating()
        val rating = rates.first().ask
        logger.info("Current rating USD to PLN: $rating")
        return CurrencyRating(BigDecimal(rating))
    }

    fun getCurrentUsdToPlnRating(): CurrencyRating {
        val (_, rates) = nbpClient.requestForUsdRating()
        val rating = rates.first().ask
        logger.info("Current rating USD to PLN: $rating")
        return CurrencyRating(BigDecimal(rating))
    }
}
