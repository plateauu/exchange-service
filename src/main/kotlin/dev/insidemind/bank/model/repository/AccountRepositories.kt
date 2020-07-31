package dev.insidemind.bank.model.repository

import dev.insidemind.bank.model.Account
import dev.insidemind.bank.model.AccountId
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Singleton

@Singleton
class AccountReadRepository(
        private val accountRepository: AccountRepository
) {
    fun find(id: AccountId): Account? {
        return accountRepository.find(id)
    }
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
