package dev.insidemind.exchange.model.event

import dev.insidemind.exchange.model.Amount
import dev.insidemind.exchange.model.Pesel

class CreateAccountEvent(
        val name: String,
        val surname: String,
        val pesel: Pesel,
        amount: Amount
) : Event(EventType.CREATE, amount)
