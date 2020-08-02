package dev.insidemind.exchange.service

import dev.insidemind.exchange.model.Account
import dev.insidemind.exchange.model.AccountId
import dev.insidemind.exchange.model.CreateAccountValidationException
import dev.insidemind.exchange.model.Pesel
import dev.insidemind.exchange.model.event.CreateAccountEvent
import dev.insidemind.exchange.model.event.CreateAccountEventResponse
import dev.insidemind.exchange.model.repository.AccountWriteRepository
import dev.insidemind.exchange.model.AccountFactory.fromEvent
import dev.insidemind.exchange.utils.annotation.Mockable
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Mockable
@Singleton
class AccountWriteService(
        private val accountWriteRepository: AccountWriteRepository,
        private val accountReadService: AccountReadService
) {
    private val logger: Logger = LoggerFactory.getLogger(AccountWriteService::class.java)

    fun createAccount(event: CreateAccountEvent): CreateAccountEventResponse {
        val pesel = event.pesel

        validatePerson(pesel)
        validateIfAccountExists(pesel)

        val account = fromEvent(event)
        accountWriteRepository.save(account)
        return CreateAccountEventResponse(account.id, account.subAccounts)
    }

    private fun validateIfAccountExists(pesel: Pesel) {
        val accountId = AccountId(pesel)
        accountReadService.findAccountFor(accountId)
                ?.let {
                    throw CreateAccountValidationException(
                            "Account for accountId: ${accountId.pesel.value} already exists. You can posses only one account"
                    )
                }
        logger.debug("AccountId $pesel is valid. Does not exist.")
    }

    private fun validatePerson(pesel: Pesel) {
        if (!pesel.isAdult())
            throw CreateAccountValidationException("Account is available for a adult person")
        logger.debug("Pesel $pesel belongs to adult person. Positive validation")
    }

    fun save(account: Account) = accountWriteRepository.save(account)
}

