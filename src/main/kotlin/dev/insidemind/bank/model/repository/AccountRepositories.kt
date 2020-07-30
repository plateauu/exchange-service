package dev.insidemind.bank.model.repository

import dev.insidemind.bank.model.Account
import dev.insidemind.bank.model.AccountId
import javax.inject.Singleton

@Singleton
class AccountReadRepository : AccountRepository() {
    override fun find(id: AccountId): Account? {
        return database[id] ?: throw RuntimeException("Account: $id does not exist")
    }
}

@Singleton
class AccountWriteRepository : AccountRepository() {
    override fun save(account: Account): Account {
        database[account.id] = account
        return account
    }
}

abstract class AccountRepository {
    protected val database: MutableMap<AccountId, Account> = mutableMapOf()
    open fun find(id: AccountId): Account? = throw RuntimeException("Not implemented")
    open fun save(account: Account): Account = throw RuntimeException("Not implemented")
}
