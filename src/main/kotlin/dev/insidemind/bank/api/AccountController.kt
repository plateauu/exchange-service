package dev.insidemind.bank.api

import dev.insidemind.bank.api.model.AccountApiResponseFactory
import dev.insidemind.bank.api.model.CreateAccountRequest
import dev.insidemind.bank.api.model.CreateAccountResponse
import dev.insidemind.bank.api.model.GetAccountBalanceResponse
import dev.insidemind.bank.service.AccountReadService
import dev.insidemind.bank.service.AccountWriteService
import dev.insidemind.bank.utils.toAccountId
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller("/account")
class AccountController(
        private val accountWriteService: AccountWriteService,
        private val accountReadService: AccountReadService,
        private val apiResponseFactory: AccountApiResponseFactory
) {
    private val logger: Logger = LoggerFactory.getLogger(AccountController::class.java)

    @Post
    fun create(@Body accountRequest: CreateAccountRequest): HttpResponse<CreateAccountResponse> {
        logger.info("Received create account request for pesel: ${accountRequest.pesel}")
        return accountWriteService.createAccount(accountRequest.toEvent())
                .let { HttpResponse.ok(apiResponseFactory.createCreateAccountResponse(it)) }
    }

    @Get("/{accountId}")
    fun getAccountBalance(@PathVariable accountId: String): HttpResponse<GetAccountBalanceResponse> {
        val id = accountId.toAccountId()
        logger.info("Received account balance request for accountId: $id")
        return accountReadService.findAccountForOrThrow(id)
                .let { HttpResponse.ok(apiResponseFactory.createGetAccountBalanceResponse(it)) }
    }
}
