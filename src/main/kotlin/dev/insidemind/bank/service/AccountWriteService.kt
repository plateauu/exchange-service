package dev.insidemind.bank.service

import dev.insidemind.bank.model.event.CreateAccountEvent
import dev.insidemind.bank.model.event.CreateAccountEventResponse
import javax.inject.Singleton

@Singleton
class AccountWriteService {
    fun createAccount(event: CreateAccountEvent): CreateAccountEventResponse {
        return CreateAccountEventResponse(event.pesel, mapOf())
    }
}

