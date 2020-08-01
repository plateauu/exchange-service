package dev.insidemind.bank.service

import dev.insidemind.bank.model.*
import dev.insidemind.bank.model.Operation.Companion.income
import dev.insidemind.bank.model.Operation.Companion.outcome
import dev.insidemind.bank.model.event.ExchangeEvent
import dev.insidemind.bank.model.event.ExchangeEventResponse
import javax.inject.Singleton

@Singleton
class ExchangeService(
        private val accountReadService: AccountReadService,
        private val accountWriteService: AccountWriteService,
        private val currencyRatingService: CurrencyRatingService
) {
    fun exchange(event: ExchangeEvent): ExchangeEventResponse {
        val account = accountReadService.findAccountForOrThrow(event.accountId)
        val from = account.subAccounts.getValue(event.from)
        val to = account.subAccounts.getValue(event.to)

        evaluateSourceAmount(event)
                .let {
                    validateSufficientAmount(from, event, it)
                    from.addOperation(outcome(it, event.effectiveDate, event.type))
                    to.addOperation(income(event.amount, event.effectiveDate, event.type))
                }

        val (id, _, _, subAccounts) = accountWriteService.save(account)
        return ExchangeEventResponse(id, subAccounts, event.effectiveDate)
    }

    private fun evaluateSourceAmount(event: ExchangeEvent): Amount {
        val rating = when (event.to) {
            Currency.PLN -> currencyRatingService.getCurrentPlnToUsdRating()
            Currency.USD -> currencyRatingService.getCurrentUsdToPlnRating()
        }
        return rating.exchange(event.amount)
    }

    private fun validateSufficientAmount(
            from: SubAccount,
            event: ExchangeEvent,
            outcome: Amount
    ) {
        if (!from.isSufficientAmount(outcome)) {
            val message = "There is not enough amount of money in ${event.from} sub account in order to exchange ${event.amount} ${event.to}. " +
                    "Current balance is: ${from.amount()} ${event.from}. You need to have $outcome ${event.from}"
            throw InsufficientSubAccountAmountException(message)
        }
    }

}
