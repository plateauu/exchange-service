package dev.insidemind.exchange.model.event

import dev.insidemind.exchange.model.AccountId
import dev.insidemind.exchange.model.Amount
import dev.insidemind.exchange.model.Currency

class ExchangeEvent(
        val accountId: AccountId,
        val to: Currency,
        val from: Currency,
        amount: Amount
) : Event(EventType.EXCHANGE, amount)
