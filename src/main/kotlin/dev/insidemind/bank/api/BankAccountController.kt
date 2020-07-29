package dev.insidemind.bank.api

import com.fasterxml.jackson.annotation.JsonValue
import dev.insidemind.bank.utils.AmountFormatter
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import java.util.*

//TODO Add TypeConverter for BigDecimal with NumberFormat (comma separated values and round to two signs)
@Controller("/account")
class BankAccountController {
    private val logger: Logger = LoggerFactory.getLogger(BankAccountController::class.java)

    @Post
    fun create(@Body request: CreateRequest): HttpResponse<CreateResponse> =
            HttpResponse.ok(CreateResponse(request.pesel, request.amount))

    @Get("/{accountId}")
    fun getAccountBalance(@PathVariable accountId: String): HttpResponse<GetAccountBalanceResponse> {
        val balance = AmountFormatter.parse("123,23")
        return HttpResponse.ok(GetAccountBalanceResponse(listOf(Balance(balance, Currency.PLN), Balance(balance, Currency.USD))))
    }
}

object PolishLocale {
    val value = Locale("PL", "pl")
}

data class GetAccountBalanceResponse(
        val balances: List<Balance>
)

data class Balance(
        val amount: BigDecimal,
        val currency: Currency
) {
    @JsonValue
    override fun toString(): String {
        return "$amount $currency"
    }
}

enum class Currency {
    PLN, USD
}

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

