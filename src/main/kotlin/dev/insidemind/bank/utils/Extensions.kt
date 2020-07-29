package dev.insidemind.bank.utils

import dev.insidemind.bank.model.Amount
import dev.insidemind.bank.model.Balance
import dev.insidemind.bank.model.Currency
import java.math.BigDecimal

fun BigDecimal.toBalance(currency: Currency) = Balance(Amount(this), currency)
