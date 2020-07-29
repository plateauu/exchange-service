package dev.insidemind.bank.api

import dev.insidemind.bank.api.model.*
import dev.insidemind.bank.model.Amount
import dev.insidemind.bank.model.Currency
import dev.insidemind.bank.service.AccountService
import dev.insidemind.bank.utils.parse
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller("/account")
class BankAccountController(
        private val accountService: AccountService,
        private val apiResponseFactory: AccountApiResponseFactory
) {
    private val logger: Logger = LoggerFactory.getLogger(BankAccountController::class.java)

    @Post
    fun create(@Body accountRequest: CreateAccountRequest): HttpResponse<CreateAccountResponse> {
        return accountService.createAccount(accountRequest.toEvent())
                .let {
                    HttpResponse.ok(apiResponseFactory.createCreateAccountResponse(it))
                }
    }

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
