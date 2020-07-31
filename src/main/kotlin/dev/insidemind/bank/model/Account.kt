package dev.insidemind.bank.model

data class Account(
        val id: AccountId,
        val name: String,
        val surname: String,
        val subAccounts: Map<Currency, SubAccount>
) {
    fun unwrapId() = id.pesel.value
}

data class AccountId(val pesel: Pesel) {
    constructor(pesel: String) : this(Pesel(pesel))

    override fun toString(): String {
        return pesel.toString()
    }
}
