package dev.insidemind.bank.model

data class AccountId(val pesel: Pesel) {
    constructor(pesel: String) : this(Pesel(pesel))

    override fun toString(): String {
        return pesel.toString()
    }
}
