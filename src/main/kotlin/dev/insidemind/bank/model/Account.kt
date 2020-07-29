package dev.insidemind.bank.model

data class Account(
        val id: AccountId,
        val balances: Map<Currency, Balance>
)

inline class AccountId(private val value: Pesel)
