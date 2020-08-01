package dev.insidemind.bank.utils

import dev.insidemind.bank.model.AccountId
import dev.insidemind.bank.model.InconsistentAccountNumberException
import dev.insidemind.bank.model.PeselValidationException

//TODO Add test
fun String.toAccountId() =
        try {
            AccountId(this)
        } catch (ex: PeselValidationException) {
            throw InconsistentAccountNumberException("Invalid account number")
        }
