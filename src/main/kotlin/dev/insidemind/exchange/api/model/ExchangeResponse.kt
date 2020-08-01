package dev.insidemind.exchange.api.model

import dev.insidemind.exchange.model.Currency
import java.time.LocalDateTime

data class ExchangeResponse(
        val accountId: String,
        val subAccounts: Map<Currency, SubAccountBalanceResponse>,
        val operationDate: LocalDateTime
)
