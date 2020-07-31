package dev.insidemind.bank.api.model

import dev.insidemind.bank.model.Account
import dev.insidemind.bank.model.Currency
import dev.insidemind.bank.model.SubAccount
import dev.insidemind.bank.model.event.CreateAccountEventResponse
import javax.inject.Singleton

@Singleton
class AccountApiResponseFactory {
    fun createCreateAccountResponse(event: CreateAccountEventResponse): CreateAccountResponse {
        val balances = mapToSubAccountBalanceResponse(event.subAccounts)
        return CreateAccountResponse(event.id.pesel.value, balances)
    }

    private fun mapToSubAccountBalanceResponse(subAccounts: Map<Currency, SubAccount>) =
            subAccounts
                    .mapValues { (_, balance) -> SubAccountBalanceResponse(WebAmount(balance.amount()), balance.currency) }

    fun createGetAccountBalanceResponse(account: Account): GetAccountBalanceResponse {
        val balances = mapToSubAccountBalanceResponse(account.subAccounts)
        return GetAccountBalanceResponse(account.unwrapId(), balances)
    }
}
