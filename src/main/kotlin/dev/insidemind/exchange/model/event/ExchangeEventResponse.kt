package dev.insidemind.exchange.model.event

import dev.insidemind.exchange.model.AccountId
import dev.insidemind.exchange.model.Currency
import dev.insidemind.exchange.model.SubAccount
import java.time.LocalDateTime

data class ExchangeEventResponse(
    val id: AccountId,
    val subAccounts: Map<Currency, SubAccount>,
    val operationDate: LocalDateTime
)
