package dev.insidemind.bank.api

import dev.insidemind.bank.api.model.AccountApiResponseFactory
import dev.insidemind.bank.api.model.ExchangeRequest
import dev.insidemind.bank.api.model.ExchangeResponse
import dev.insidemind.bank.model.Currency
import dev.insidemind.bank.model.Currency.PLN
import dev.insidemind.bank.model.Currency.USD
import dev.insidemind.bank.service.ExchangeService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Put
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller("/account")
class ExchangeController(
        private val exchangeService: ExchangeService,
        private val apiResponseFactory: AccountApiResponseFactory
) {
    private val logger: Logger = LoggerFactory.getLogger(ExchangeController::class.java)

    @Put("/usd")
    fun exchangeToUsd(@Body exchangeRequest: ExchangeRequest): HttpResponse<ExchangeResponse> {
        logger.info("Received exchange from PLN to USD request for account: ${exchangeRequest.accountId} and amount: ${exchangeRequest.amount}")
        return exchange(exchangeRequest, PLN, USD)
    }

    @Put("/pln")
    fun exchangeToPln(@Body exchangeRequest: ExchangeRequest): HttpResponse<ExchangeResponse> {
        logger.info("Received exchange from USD to PLN request for account: ${exchangeRequest.accountId} and amount: ${exchangeRequest.amount}")
        return exchange(exchangeRequest, USD, PLN)
    }

    private fun exchange(
            exchangeRequest: ExchangeRequest,
            from: Currency,
            to: Currency
    ): HttpResponse<ExchangeResponse> =
        exchangeService.exchange(exchangeRequest.toEvent(from, to))
                .let { HttpResponse.ok(apiResponseFactory.createExchangeResponse(it)) }
}

