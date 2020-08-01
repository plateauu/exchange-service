package dev.insidemind.exchange.service

import dev.insidemind.exchange.model.Amount
import dev.insidemind.exchange.model.Currency
import dev.insidemind.exchange.model.InsufficientSubAccountAmountException
import dev.insidemind.exchange.model.Operation.Companion.income
import dev.insidemind.exchange.model.Operation.Companion.outcome
import dev.insidemind.exchange.model.SubAccount
import dev.insidemind.exchange.model.event.ExchangeEvent
import dev.insidemind.exchange.model.event.ExchangeEventResponse
import dev.insidemind.exchange.service.rating.CurrencyRatingService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class ExchangeService(
        private val accountReadService: AccountReadService,
        private val accountWriteService: AccountWriteService,
        private val currencyRatingService: CurrencyRatingService
) {
    private val logger: Logger = LoggerFactory.getLogger(ExchangeService::class.java)

    fun exchange(event: ExchangeEvent): ExchangeEventResponse {
        val account = accountReadService.findAccountForOrThrow(event.accountId)
        val from = account.subAccounts.getValue(event.from)
        val to = account.subAccounts.getValue(event.to)

        evaluateSourceAmount(event)
                .let {
                    logger.debug("AccountId: ${event.accountId} needs to have $it ${event.from} to make an exchange")
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
            logger.debug(message)
            throw InsufficientSubAccountAmountException(message)
        }
        logger.debug("AccountId: ${event.accountId} has sufficient amount of money to exchange ${event.amount} ${event.to}")
    }

}
