package dev.insidemind.exchange.api.model

import dev.insidemind.exchange.model.Amount
import dev.insidemind.exchange.model.Pesel
import dev.insidemind.exchange.model.event.CreateAccountEvent

data class CreateAccountRequest(
        val name: String,
        val surname: String,
        val pesel: String,
        val amount: WebAmount
) {
    fun toEvent(): CreateAccountEvent = CreateAccountEvent(
            name = this.name,
            surname = this.surname,
            pesel = Pesel(this.pesel),
            amount = Amount(amount.value)
    )
}
