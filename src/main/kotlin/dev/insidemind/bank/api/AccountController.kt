package dev.insidemind.bank.api

import dev.insidemind.bank.api.model.AccountApiResponseFactory
import dev.insidemind.bank.api.model.CreateAccountRequest
import dev.insidemind.bank.api.model.CreateAccountResponse
import dev.insidemind.bank.api.model.GetAccountBalanceResponse
import dev.insidemind.bank.model.AccountId
import dev.insidemind.bank.model.InconsistentAccountNumberException
import dev.insidemind.bank.model.PeselValidationException
import dev.insidemind.bank.service.AccountReadService
import dev.insidemind.bank.service.AccountWriteService
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
        val id = getAccountId(accountId)
        logger.info("Received account balance request for accountId: $id")
        return accountReadService.findAccountForOrThrow(id)
                .let { HttpResponse.ok(apiResponseFactory.createGetAccountBalanceResponse(it)) }
    }

    private fun getAccountId(accountId: String): AccountId =
            try {
                AccountId(accountId)
            } catch (ex: PeselValidationException) {
                throw InconsistentAccountNumberException("Invalid account number")
            }
}
