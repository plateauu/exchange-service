package dev.insidemind.exchange.model.repository

import dev.insidemind.exchange.model.Account
import dev.insidemind.exchange.model.AccountId
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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

    private val logger: Logger = LoggerFactory.getLogger(AccountRepository::class.java)

    fun find(id: AccountId): Account? {
        val account = database[id]

        if (logger.isDebugEnabled)
            logResult(account, id)

        return account
    }

    private fun logResult(account: Account?, id: AccountId) {
        if (account == null) {
            logger.debug("Account $id not found")
        } else {
            logger.debug("Account $id has been found")
        }
    }

    fun save(account: Account): Account {
        database[account.id] = account
        logger.debug("Account ${account.id} saved")
        return account
    }
}
