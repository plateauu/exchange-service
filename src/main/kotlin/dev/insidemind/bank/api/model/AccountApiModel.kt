package dev.insidemind.bank.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import dev.insidemind.bank.model.Amount
import dev.insidemind.bank.model.Currency
import dev.insidemind.bank.model.Pesel
import dev.insidemind.bank.model.event.CreateAccountEvent
import dev.insidemind.bank.utils.format
import dev.insidemind.bank.utils.parse
import java.math.BigDecimal
import javax.inject.Singleton

data class GetAccountBalanceResponse(
        val balances: Map<Currency, BalanceResponse>
)

data class BalanceResponse(
        val amount: WebAmount,
        val currency: Currency
) {
    @JsonValue
    override fun toString(): String {
        return "$amount $currency"
    }
}

data class CreateAccountResponse(
        val accountId: String,
        val balances: Map<Currency, BalanceResponse>
)

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

class WebAmount(@JsonProperty amount: String) {
    constructor(amount: Amount) : this(amount.format())

    val value: BigDecimal = amount.parse()

    @JsonValue
    override fun toString() = value.format()
}
