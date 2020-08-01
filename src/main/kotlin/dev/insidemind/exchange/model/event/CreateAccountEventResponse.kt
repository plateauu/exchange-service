package dev.insidemind.exchange.model.event

import dev.insidemind.exchange.model.AccountId
import dev.insidemind.exchange.model.SubAccount
import dev.insidemind.exchange.model.Currency

data class CreateAccountEventResponse(
        val id: AccountId,
        val subAccounts: Map<Currency, SubAccount>
)
