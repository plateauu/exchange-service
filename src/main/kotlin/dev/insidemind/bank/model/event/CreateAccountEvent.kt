package dev.insidemind.bank.model.event

import dev.insidemind.bank.model.Amount
import dev.insidemind.bank.model.Pesel

data class CreateAccountEvent(
        val name: String,
        val surname: String,
        val pesel: Pesel,
        val amount: Amount
) {
    fun isZero() = amount.isZero()
}
