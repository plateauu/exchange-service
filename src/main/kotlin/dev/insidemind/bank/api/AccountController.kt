package dev.insidemind.bank.api

import dev.insidemind.bank.api.model.*
import dev.insidemind.bank.model.Currency
import dev.insidemind.bank.service.AccountWriteService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller("/account")
class AccountController(
        private val accountWriteService: AccountWriteService,
        private val apiResponseFactory: AccountApiResponseFactory
) {
    private val logger: Logger = LoggerFactory.getLogger(AccountController::class.java)

    @Post
    fun create(@Body accountRequest: CreateAccountRequest): HttpResponse<CreateAccountResponse> {
        logger.info("Received request: ${accountRequest::class.simpleName} for pesel: ${accountRequest.pesel}")
        return accountWriteService.createAccount(accountRequest.toEvent())
                .let { HttpResponse.ok(apiResponseFactory.createCreateAccountResponse(it)) }
    }

    @Get("/{accountId}")
    fun getAccountBalance(@PathVariable accountId: String): HttpResponse<GetAccountBalanceResponse> {
        val balance = "123,23"
        return HttpResponse.ok(GetAccountBalanceResponse(
                mapOf(
                        Currency.PLN to BalanceResponse(WebAmount(balance), Currency.PLN),
                        Currency.USD to BalanceResponse(WebAmount(balance), Currency.USD)
                )
        ))
    }
}
