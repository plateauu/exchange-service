package dev.insidemind.bank.service

import dev.insidemind.bank.model.Account
import dev.insidemind.bank.model.AccountId
import dev.insidemind.bank.model.repository.AccountReadRepository
import dev.insidemind.bank.utils.annotation.Mockable
import java.util.*
import javax.inject.Singleton

@Mockable
@Singleton
class AccountReadService(
        private val accountReadRepository: AccountReadRepository
)  {
    fun findAccountFor(accountId: AccountId): Account? =
            accountReadRepository.find(accountId)

}
