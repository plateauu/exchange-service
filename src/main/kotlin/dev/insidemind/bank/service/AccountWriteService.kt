package dev.insidemind.bank.service

import dev.insidemind.bank.model.Account
import dev.insidemind.bank.model.AccountId
import dev.insidemind.bank.model.CreateAccountValidationException
import dev.insidemind.bank.model.Pesel
import dev.insidemind.bank.model.event.CreateAccountEvent
import dev.insidemind.bank.model.event.CreateAccountEventResponse
import dev.insidemind.bank.model.repository.AccountWriteRepository
import javax.inject.Singleton

@Singleton
class AccountWriteService(
        private val accountWriteRepository: AccountWriteRepository,
        private val accountReadService: AccountReadService,
        private val accountFactory: AccountFactory
) {
    fun createAccount(event: CreateAccountEvent): CreateAccountEventResponse {
        val pesel = event.pesel

        validatePerson(pesel)
        validateIfAccountExists(pesel)

        val account = accountFactory.fromEvent(event)
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
    }

    private fun validatePerson(pesel: Pesel) {
        if (!pesel.isAdult())
            throw CreateAccountValidationException("Account is available for a adult person")
    }

    fun save(account: Account) = accountWriteRepository.save(account)
}

