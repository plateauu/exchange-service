package dev.insidemind.exchange.service

import dev.insidemind.exchange.model.Account
import dev.insidemind.exchange.model.AccountId
import dev.insidemind.exchange.model.AccountNotFountException
import dev.insidemind.exchange.model.repository.AccountReadRepository
import dev.insidemind.exchange.utils.annotation.Mockable
import javax.inject.Singleton

@Mockable
@Singleton
class AccountReadService(
        private val accountReadRepository: AccountReadRepository
)  {
    fun findAccountFor(accountId: AccountId): Account? =
            accountReadRepository.find(accountId)

    fun findAccountForOrThrow(accountId: AccountId): Account =
            accountReadRepository.find(accountId)
                    ?: throw AccountNotFountException("Account ${accountId.pesel} does not exist")

}
