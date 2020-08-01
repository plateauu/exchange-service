package dev.insidemind.exchange.api

import dev.insidemind.exchange.api.model.ApiResponseFactory
import dev.insidemind.exchange.api.model.ApiResponseFactory.createCreateAccountResponse
import dev.insidemind.exchange.api.model.ApiResponseFactory.createGetAccountBalanceResponse
import dev.insidemind.exchange.api.model.CreateAccountRequest
import dev.insidemind.exchange.api.model.CreateAccountResponse
import dev.insidemind.exchange.api.model.GetAccountBalanceResponse
import dev.insidemind.exchange.service.AccountReadService
import dev.insidemind.exchange.service.AccountWriteService
import dev.insidemind.exchange.utils.toAccountId
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller("/account")
class AccountController(
        private val accountWriteService: AccountWriteService,
        private val accountReadService: AccountReadService
) {
    private val logger: Logger = LoggerFactory.getLogger(AccountController::class.java)

    @ApiResponses(
            ApiResponse(description = "Account created", responseCode = "200",
                    content = [Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = Schema(implementation = CreateAccountResponse::class)
                    )]),
            ApiResponse(description = "Account already exists", responseCode = "422"),
            ApiResponse(description = "Bad PESEL data", responseCode = "412")
    )
    @Produces(MediaType.APPLICATION_JSON)
    @Post
    fun create(@Body accountRequest: CreateAccountRequest): HttpResponse<CreateAccountResponse> {
        logger.info("Received create account request for pesel: ${accountRequest.pesel}")
        return accountWriteService.createAccount(accountRequest.toEvent())
                .let { HttpResponse.ok(createCreateAccountResponse(it)) }
    }

    @ApiResponses(
            ApiResponse(description = "Account balances", responseCode = "200",
                    content = [Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = Schema(implementation = GetAccountBalanceResponse::class)
                    )]),
            ApiResponse(description = "Illegal account number passed", responseCode = "412"),
            ApiResponse(description = "Account not found", responseCode = "412")
    )
    @Produces(MediaType.APPLICATION_JSON)
    @Get("/{accountId}")
    fun getAccountBalance(@PathVariable accountId: String): HttpResponse<GetAccountBalanceResponse> {
        val id = accountId.toAccountId()
        logger.info("Received account balance request for accountId: $id")
        return accountReadService.findAccountForOrThrow(id)
                .let { HttpResponse.ok(createGetAccountBalanceResponse(it)) }
    }
}
