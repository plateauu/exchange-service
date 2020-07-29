package dev.insidemind.bank.service

import dev.insidemind.bank.model.*
import dev.insidemind.bank.model.event.CreateAccountEvent
import dev.insidemind.bank.utils.toSubAccount
import java.math.BigDecimal
import javax.inject.Singleton

@Singleton
class AccountFactory(
        private val currencyRatingService: CurrencyRatingService
) {

    fun fromEvent(event: CreateAccountEvent) : Account{
        val subAccounts: Map<Currency, SubAccount> = createSubAccounts(event)

        return Account(
                id = AccountId(event.pesel),
                surname = event.surname,
                name = event.name,
                subAccounts = subAccounts
        )
    }

    private fun createSubAccounts(event: CreateAccountEvent): Map<Currency, SubAccount>  {
        return if (event.isZero()) {
            createZeroSubAccount()
        } else {
            val amount = event.amount
            val pln = Currency.PLN to amount
            val usd = Currency.USD to Amount(BigDecimal.ZERO)
            return mapOf(pln, usd).mapValues { (currency, amount) -> SubAccount(amount,currency) }
        }
    }

    private fun createZeroSubAccount(): Map<Currency, SubAccount> {
        return Currency.values()
                .map { it to BigDecimal.ZERO.toSubAccount(it) }
                .toMap()
    }
}
