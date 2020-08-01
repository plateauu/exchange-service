package dev.insidemind.exchange.model.repository

import dev.insidemind.exchange.model.Account
import dev.insidemind.exchange.model.AccountId
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Singleton

@Singleton
class AccountReadRepository(
        private val accountRepository: AccountRepository
) {
    fun find(id: AccountId): Account? =
            accountRepository.find(id)

}

@Singleton
class AccountWriteRepository(
        private val accountRepository: AccountRepository
) {
    fun save(account: Account): Account =
            accountRepository.save(account)
}

@Singleton
class AccountRepository {
    private val database: MutableMap<AccountId, Account> = ConcurrentHashMap()

    fun find(id: AccountId): Account? = database[id]

    fun save(account: Account): Account {
        database[account.id] = account
        return account
    }
}
