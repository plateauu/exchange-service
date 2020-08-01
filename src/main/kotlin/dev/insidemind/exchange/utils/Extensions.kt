package dev.insidemind.exchange.utils

import dev.insidemind.exchange.model.AccountId
import dev.insidemind.exchange.model.InconsistentAccountNumberException
import dev.insidemind.exchange.model.PeselValidationException

//TODO Add test
fun String.toAccountId() =
        try {
            AccountId(this)
        } catch (ex: PeselValidationException) {
            throw InconsistentAccountNumberException("Invalid account number")
        }
