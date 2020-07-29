package dev.insidemind.bank.api.model

import dev.insidemind.bank.model.event.CreateAccountEventResponse
import javax.inject.Singleton

@Singleton
class AccountApiResponseFactory {
    fun createCreateAccountResponse(event: CreateAccountEventResponse): CreateAccountResponse {
        val balances = event.balances
                .mapValues { (_, balance) -> BalanceResponse(balance.amount, balance.currency) }
        return CreateAccountResponse(event.id.pesel.value, balances)
    }
}
