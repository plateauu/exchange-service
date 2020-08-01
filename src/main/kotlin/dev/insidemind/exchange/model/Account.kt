package dev.insidemind.exchange.model

data class Account(
        val id: AccountId,
        val name: String,
        val surname: String,
        val subAccounts: Map<Currency, SubAccount>
) {
    fun unwrapId() = id.unwrap()
}

