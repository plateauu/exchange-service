package dev.insidemind.exchange.api.model

import com.fasterxml.jackson.annotation.JsonValue
import dev.insidemind.exchange.model.Currency

data class SubAccountBalanceResponse(
        val amount: WebAmount,
        val currency: Currency
) {
    @JsonValue
    override fun toString(): String {
        return "$amount $currency"
    }
}
