package dev.insidemind.bank.model

import dev.insidemind.bank.utils.format
import java.math.BigDecimal

data class Amount(private val value: BigDecimal) {

    fun isZero() = value.compareTo(BigDecimal.ZERO) == 0

    fun multiply(other: BigDecimal): Amount = Amount(value.multiply(other))

    fun format() = value.format()
}
