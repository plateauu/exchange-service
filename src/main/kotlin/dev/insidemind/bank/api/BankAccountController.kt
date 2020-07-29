package dev.insidemind.bank.api

import dev.insidemind.bank.api.model.BalanceResponse
import dev.insidemind.bank.api.model.CreateRequest
import dev.insidemind.bank.api.model.CreateResponse
import dev.insidemind.bank.api.model.GetAccountBalanceResponse
import dev.insidemind.bank.model.Amount
import dev.insidemind.bank.model.Currency
import dev.insidemind.bank.utils.parse
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller("/account")
class BankAccountController {
    private val logger: Logger = LoggerFactory.getLogger(BankAccountController::class.java)

    @Post
    fun create(@Body request: CreateRequest): HttpResponse<CreateResponse> =
            HttpResponse.ok(CreateResponse(request.pesel, request.amount))

    @Get("/{accountId}")
    fun getAccountBalance(@PathVariable accountId: String): HttpResponse<GetAccountBalanceResponse> {
        val balance = "123,23".parse()
        return HttpResponse.ok(GetAccountBalanceResponse(
                mapOf(
                        Currency.PLN to BalanceResponse(Amount(balance), Currency.PLN),
                        Currency.USD to BalanceResponse(Amount(balance), Currency.USD)
                )
        ))
    }
}

