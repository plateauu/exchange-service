package dev.insidemind.bank.api.model

import dev.insidemind.bank.model.event.CreateAccountEventResponse
import javax.inject.Singleton

@Singleton
class AccountApiResponseFactory {
    fun createCreateAccountResponse(event: CreateAccountEventResponse): CreateAccountResponse {
        val balances = event.subAccounts
                .mapValues { (_, balance) -> SubAccountBalanceResponse(WebAmount(balance.amount), balance.currency) }
        return CreateAccountResponse(event.id.pesel.value, balances)
    }
}
