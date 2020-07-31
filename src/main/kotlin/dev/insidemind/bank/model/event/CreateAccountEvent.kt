package dev.insidemind.bank.model.event

import dev.insidemind.bank.model.Amount
import dev.insidemind.bank.model.Pesel

class CreateAccountEvent(
        val name: String,
        val surname: String,
        val pesel: Pesel,
        amount: Amount
) : Event(EventType.CREATE, amount)
