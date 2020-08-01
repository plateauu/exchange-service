package dev.insidemind.bank.model.event

import dev.insidemind.bank.model.AccountId
import dev.insidemind.bank.model.Currency
import dev.insidemind.bank.model.SubAccount
import java.time.LocalDateTime

data class ExchangeEventResponse(
    val id: AccountId,
    val subAccounts: Map<Currency, SubAccount>,
    val operationDate: LocalDateTime
)
