package dev.insidemind.bank.api

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*

//TODO Add TypeConverter for BigDecimal with NumberFormat (comma separated values and round to two signs)
@Controller("/account")
class BankAccountController {
    private val logger: Logger = LoggerFactory.getLogger(BankAccountController::class.java )

    @Post
    fun create(@Body request: CreateRequest): HttpResponse<CreateResponse> =
            HttpResponse.ok(CreateResponse(request.pesel, request.amount))

    @Get("/{accountId}")
    fun getAccountBalance(@PathVariable accountId: String): HttpResponse<GetAccountBalanceResponse> {
        val amountInPln = NumberFormat.getNumberInstance(Locale("PL", "pl")).parse("123,23523")
        logger.info("amount: $amountInPln")
        val toSend = BigDecimal(amountInPln.toString()).setScale(2, RoundingMode.HALF_UP)
        logger.info("to send: $toSend")

        return HttpResponse.ok(GetAccountBalanceResponse(toSend, BigDecimal(40.2)))
    }
}

data class GetAccountBalanceResponse(
        val amountInPln: BigDecimal,
        val amountInUsd: BigDecimal
)

data class CreateResponse(
        val accountId: String,
        val amount: BigDecimal
)

data class CreateRequest(
        val name: String,
        val surname: String,
        val pesel: String,
        val amount: BigDecimal
)

