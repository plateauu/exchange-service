package dev.insidemind.bank.model.event

import dev.insidemind.bank.model.AccountId
import dev.insidemind.bank.model.Balance
import dev.insidemind.bank.model.Currency
import dev.insidemind.bank.model.Pesel

data class CreateAccountEventResponse(
        val id: AccountId,
        val balances: Map<Currency, Balance>
)
