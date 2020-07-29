package dev.insidemind.bank.api.model

import com.fasterxml.jackson.annotation.JsonValue
import dev.insidemind.bank.model.Amount
import dev.insidemind.bank.model.Currency
import java.math.BigDecimal

data class GetAccountBalanceResponse(
        val balances: Map<Currency, BalanceResponse>
)

data class BalanceResponse(
        val amount: Amount,
        val currency: Currency
) {
    @JsonValue
    override fun toString(): String {
        return "$amount $currency"
    }
}

data class CreateResponse(
        val accountId: String,
        val balances: Map<Currency, BalanceResponse>
)

data class CreateRequest(
        val name: String,
        val surname: String,
        val pesel: String,
        val amount: BigDecimal
)
