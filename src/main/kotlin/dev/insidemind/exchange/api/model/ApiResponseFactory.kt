package dev.insidemind.exchange.api.model

import dev.insidemind.exchange.model.Account
import dev.insidemind.exchange.model.Currency
import dev.insidemind.exchange.model.SubAccount
import dev.insidemind.exchange.model.event.CreateAccountEventResponse
import dev.insidemind.exchange.model.event.ExchangeEventResponse
import javax.inject.Singleton

object ApiResponseFactory {

    fun createCreateAccountResponse(event: CreateAccountEventResponse): CreateAccountResponse {
        val balances = mapToSubAccountBalanceResponse(event.subAccounts)
        return CreateAccountResponse(event.id.unwrap(), balances)
    }

    private fun mapToSubAccountBalanceResponse(subAccounts: Map<Currency, SubAccount>) =
            subAccounts
                    .mapValues { (_, balance) -> SubAccountBalanceResponse(WebAmount(balance.amount()), balance.currency) }

    fun createGetAccountBalanceResponse(account: Account): GetAccountBalanceResponse {
        val balances = mapToSubAccountBalanceResponse(account.subAccounts)
        return GetAccountBalanceResponse(account.unwrapId(), balances)
    }

    fun createExchangeResponse(event: ExchangeEventResponse): ExchangeResponse {
        val balances = mapToSubAccountBalanceResponse(event.subAccounts)
        return ExchangeResponse(event.id.unwrap(), balances, event.operationDate)
    }
}
