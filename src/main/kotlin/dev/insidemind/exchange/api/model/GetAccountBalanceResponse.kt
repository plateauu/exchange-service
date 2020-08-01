package dev.insidemind.exchange.api.model

import dev.insidemind.exchange.model.Currency

data class GetAccountBalanceResponse(
        val accountId: String,
        val subAccounts: Map<Currency, SubAccountBalanceResponse>
)
