package dev.insidemind.bank.service

import dev.insidemind.bank.model.Balance
import dev.insidemind.bank.model.event.CreateAccountEvent
import dev.insidemind.bank.model.Currency
import dev.insidemind.bank.model.Pesel
import javax.inject.Singleton

@Singleton
class AccountService {
    fun createAccount(event: CreateAccountEvent): CreateAccountEventResponse {
        return CreateAccountEventResponse(event.pesel, mapOf())
    }
}

data class CreateAccountEventResponse(
        val accountId: Pesel,
        val balances: Map<Currency, Balance>
)
