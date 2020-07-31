package dev.insidemind.bank.model

import dev.insidemind.bank.utils.format
import java.math.BigDecimal

data class Amount(private val value: BigDecimal) {

    companion object{
        val ZERO = Amount(BigDecimal.ZERO)
    }

    fun isZero() = value.compareTo(BigDecimal.ZERO) == 0

    fun multiply(other: Amount): Amount = Amount(value.multiply(other.value))

    fun format() = value.format()

    fun add(other: Amount) : Amount = Amount(value.add(other.value))
}
