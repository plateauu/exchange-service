package dev.insidemind.bank.service

import dev.insidemind.bank.model.*
import dev.insidemind.bank.model.event.CreateAccountEvent
import java.math.BigDecimal
import javax.inject.Singleton

@Singleton
class AccountFactory(
        private val currencyRatingService: CurrencyRatingService
) {

    fun fromEvent(event: CreateAccountEvent): Account {
        val subAccounts: Map<Currency, SubAccount> = createSubAccounts(event)

        return Account(
                id = AccountId(event.pesel),
                surname = event.surname,
                name = event.name,
                subAccounts = subAccounts
        )
    }

    private fun createSubAccounts(event: CreateAccountEvent): Map<Currency, SubAccount> {
        return if (event.isZero()) {
            createZeroSubAccount(event)
        } else {
            val amount = event.amount
            val pln = Currency.PLN to amount
            val usd = Currency.USD to Amount(BigDecimal.ZERO)
            return mapOf(pln, usd).mapValues { (currency, amount) ->
                SubAccount(currency, Operation.income(amount, event.timestamp, event.type))
            }
        }
    }

    private fun createZeroSubAccount(event: CreateAccountEvent): Map<Currency, SubAccount> {
        return Currency.values()
                .map { it to SubAccount(it, Operation.income(Amount.ZERO, event.timestamp, event.type)) }
                .toMap()
    }
}
