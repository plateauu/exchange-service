package dev.insidemind.bank.model.event

import dev.insidemind.bank.model.AccountId
import dev.insidemind.bank.model.SubAccount
import dev.insidemind.bank.model.Currency

data class CreateAccountEventResponse(
        val id: AccountId,
        val subAccounts: Map<Currency, SubAccount>
)
