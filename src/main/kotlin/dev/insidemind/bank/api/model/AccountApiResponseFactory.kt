package dev.insidemind.bank.api.model

import dev.insidemind.bank.service.CreateAccountEventResponse
import javax.inject.Singleton

@Singleton
class AccountApiResponseFactory {
    fun createCreateAccountResponse(event: CreateAccountEventResponse): CreateAccountResponse {
        val balances = event.balances
                .mapValues { (_, balance) -> BalanceResponse(balance.amount, balance.currency) }
        return CreateAccountResponse(event.accountId.value, balances)
    }
}
