package dev.insidemind.bank.model.event

import dev.insidemind.bank.model.AccountId
import dev.insidemind.bank.model.Amount
import dev.insidemind.bank.model.Currency

class ExchangeEvent(
        val accountId: AccountId,
        val toCurrency: Currency,
        val fromCurrency: Currency,
        amount: Amount
) : Event(EventType.EXCHANGE, amount)
