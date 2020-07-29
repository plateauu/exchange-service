package dev.insidemind.bank.utils

import dev.insidemind.bank.model.Amount
import dev.insidemind.bank.model.SubAccount
import dev.insidemind.bank.model.Currency
import java.math.BigDecimal

fun BigDecimal.toSubAccount(currency: Currency) = SubAccount(Amount(this), currency)
