package dev.insidemind.bank.model

data class Account(
        val id: AccountId,
        val name: String,
        val surname: String,
        val balances: Map<Currency, Balance>
)

inline class AccountId(val pesel: Pesel)
