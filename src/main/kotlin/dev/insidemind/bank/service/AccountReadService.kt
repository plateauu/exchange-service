package dev.insidemind.bank.service

import dev.insidemind.bank.model.Account
import dev.insidemind.bank.model.AccountId
import dev.insidemind.bank.model.repository.AccountReadRepository
import javax.inject.Singleton

@Singleton
class AccountReadService(
        private val accountReadRepository: AccountReadRepository
)  {
    fun findAccountFor(accountId: AccountId) : Account? =
      accountReadRepository.find(accountId)


}
