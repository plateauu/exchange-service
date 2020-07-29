package dev.insidemind.bank.model

import dev.insidemind.bank.utils.toBalance
import java.math.BigDecimal

data class Balance(
    val amount: Amount,
    val currency: Currency
) {
    companion object {
        val PLN_ZERO = BigDecimal.ZERO.toBalance(Currency.PLN)
        val USD_ZERO = BigDecimal.ZERO.toBalance(Currency.USD)
    }
}

