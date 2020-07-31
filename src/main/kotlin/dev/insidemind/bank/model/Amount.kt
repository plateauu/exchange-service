package dev.insidemind.bank.model

import dev.insidemind.bank.utils.format
import java.math.BigDecimal

class Amount(val value: BigDecimal) {

    fun isZero() = value.compareTo(BigDecimal.ZERO) == 0

    fun multiply(other: BigDecimal): Amount = Amount(value.multiply(other))

    fun format() = value.format()
}
