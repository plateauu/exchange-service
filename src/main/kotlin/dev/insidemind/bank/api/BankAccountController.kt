package dev.insidemind.bank.api

import com.fasterxml.jackson.annotation.JsonValue
import dev.insidemind.bank.utils.AmountFormatter
import dev.insidemind.bank.utils.format
import dev.insidemind.bank.utils.parse
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import java.util.*

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
                        Currency.PLN to Balance(Amount(balance), Currency.PLN),
                        Currency.USD to Balance(Amount(balance), Currency.USD)
                )
        ))
    }
}

object PolishLocale {
    val value = Locale("PL", "pl")
}

data class GetAccountBalanceResponse(
        val balances: Map<Currency, Balance>
)

data class Balance(
        val amount: Amount,
        val currency: Currency
) {
    @JsonValue
    override fun toString(): String {
        return "$amount $currency"
    }
}

inline class Amount(private val value: BigDecimal) {
    override fun toString() = value.format()
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
