package dev.insidemind.exchange.api.model

import dev.insidemind.exchange.model.Amount
import dev.insidemind.exchange.model.Currency
import dev.insidemind.exchange.model.event.ExchangeEvent
import dev.insidemind.exchange.utils.toAccountId

data class ExchangeRequest(
        val accountId: String,
        val amount: WebAmount
) {
    fun toEvent(from: Currency, to: Currency): ExchangeEvent = ExchangeEvent(
            accountId = accountId.toAccountId(),
            amount = Amount(amount.value),
            from = from,
            to = to
    )
}
