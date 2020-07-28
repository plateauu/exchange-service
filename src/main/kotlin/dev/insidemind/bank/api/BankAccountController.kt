package dev.insidemind.bank.api

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.math.BigDecimal

@Controller("/account")
class BankAccountController {
    private val logger: Logger = LoggerFactory.getLogger(BankAccountController::class.java )

    @Post
    fun create(@Body request: CreateRequest): HttpResponse<CreateResponse> =
            HttpResponse.ok(CreateResponse(request.pesel, request.amount))
}

data class CreateResponse(
        val accountNumber: String,
        val amount: BigDecimal
)

data class CreateRequest(
        val name: String,
        val surname: String,
        val pesel: String,
        val amount: BigDecimal
)

