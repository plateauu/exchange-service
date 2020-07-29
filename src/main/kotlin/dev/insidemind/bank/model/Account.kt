package dev.insidemind.bank.model

data class Account(
        val id: AccountId,
        val name: String,
        val surname: String,
        val subAccounts: Map<Currency, SubAccount>
)

inline class AccountId(val pesel: Pesel)
