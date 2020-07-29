package dev.insidemind.bank.model.event

import dev.insidemind.bank.model.Balance
import dev.insidemind.bank.model.Currency
import dev.insidemind.bank.model.Pesel

data class CreateAccountEventResponse(
        val accountId: Pesel,
        val balances: Map<Currency, Balance>
)
