package dev.insidemind.exchange.model

data class AccountId(val pesel: Pesel) {
    constructor(pesel: String) : this(Pesel(pesel))

    override fun toString(): String {
        return pesel.toString()
    }

    fun unwrap() = pesel.value
}
