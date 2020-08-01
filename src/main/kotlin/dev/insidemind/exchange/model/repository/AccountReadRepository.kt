package dev.insidemind.exchange.model.repository

import dev.insidemind.exchange.model.Account
import dev.insidemind.exchange.model.AccountId
import javax.inject.Singleton

@Singleton
class AccountReadRepository(
        private val accountRepository: AccountRepository
) {
    fun find(id: AccountId): Account? =
            accountRepository.find(id)

}
