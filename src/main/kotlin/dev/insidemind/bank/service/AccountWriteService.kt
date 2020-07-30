package dev.insidemind.bank.service

import dev.insidemind.bank.model.event.CreateAccountEvent
import dev.insidemind.bank.model.event.CreateAccountEventResponse
import dev.insidemind.bank.model.repository.AccountWriteRepository
import javax.inject.Singleton

@Singleton
class AccountWriteService(
        private val accountWriteRepository: AccountWriteRepository,
        private val accountFactory: AccountFactory
) {
    fun createAccount(event: CreateAccountEvent): CreateAccountEventResponse {
        event.pesel.validate()

        val account = accountFactory.fromEvent(event)
        accountWriteRepository.save(account)
        return CreateAccountEventResponse(account.id, account.subAccounts)
    }
}

