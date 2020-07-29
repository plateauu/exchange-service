package dev.insidemind.bank.model

import dev.insidemind.bank.utils.format
import java.math.BigDecimal

inline class Amount(private val value: BigDecimal) {

    fun isZero() = value.compareTo(BigDecimal.ZERO) == 0

    fun multiply(other: BigDecimal): Amount = Amount(value.multiply(other))

    override fun toString() = value.format()
}
