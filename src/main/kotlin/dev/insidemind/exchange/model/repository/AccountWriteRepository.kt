package dev.insidemind.exchange.model.repository

import dev.insidemind.exchange.model.Account
import javax.inject.Singleton

@Singleton
class AccountWriteRepository(
        private val accountRepository: AccountRepository
) {
    fun save(account: Account): Account =
            accountRepository.save(account)
}
