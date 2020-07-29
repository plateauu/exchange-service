package dev.insidemind.bank.service

import dev.insidemind.bank.model.*
import dev.insidemind.bank.model.event.CreateAccountEvent
import dev.insidemind.bank.utils.toBalance
import java.math.BigDecimal
import javax.inject.Singleton

@Singleton
class AccountFactory(
        private val currencyRatingService: CurrencyRatingService
) {

    fun fromEvent(event: CreateAccountEvent) : Account{
        val balances: Map<Currency, Balance> = createBalance(event)

        return Account(
                id = AccountId(event.pesel),
                surname = event.surname,
                name = event.name,
                balances = balances
        )
    }

    private fun createBalance(event: CreateAccountEvent): Map<Currency, Balance>  {
        return if (event.isZero()) {
            createZeroBalance()
        } else {
            val amount = event.amount
            val pln = Currency.PLN to amount
            val usd = Currency.USD to Amount(BigDecimal.ZERO)
            return mapOf(pln, usd).mapValues { (currency, amount) -> Balance(amount,currency) }
        }
    }

    private fun createZeroBalance(): Map<Currency, Balance> {
        return Currency.values()
                .map { it to BigDecimal.ZERO.toBalance(it) }
                .toMap()
    }
}