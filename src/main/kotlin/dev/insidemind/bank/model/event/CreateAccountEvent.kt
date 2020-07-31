package dev.insidemind.bank.model.event

import dev.insidemind.bank.model.AccountId
import dev.insidemind.bank.model.Amount
import dev.insidemind.bank.model.Currency
import dev.insidemind.bank.model.Pesel
import java.time.LocalDateTime

class ExchangeEvent(
        val accountId: AccountId,
        val toCurrency: Currency,
        val fromCurrency: Currency,
        amount: Amount
) : Event(EventType.EXCHANGE, amount)

class CreateAccountEvent(
        val name: String,
        val surname: String,
        val pesel: Pesel,
        amount: Amount
) : Event(EventType.CREATE, amount)

open class Event(val type: EventType, val amount: Amount){
    val timestamp: LocalDateTime = LocalDateTime.now()
    fun isZero() = amount.isZero()
}

enum class EventType {
    CREATE, EXCHANGE
}
