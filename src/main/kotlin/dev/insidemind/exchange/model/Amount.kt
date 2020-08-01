package dev.insidemind.exchange.model

import dev.insidemind.exchange.utils.format
import java.math.BigDecimal
import java.math.RoundingMode

class Amount(amount: BigDecimal) {

    private val value = amount.setScale(2, RoundingMode.HALF_UP)

    companion object{
        val ZERO = Amount(BigDecimal.ZERO)
    }

    fun isZero() = value.compareTo(BigDecimal.ZERO) == 0

    fun multiply(other: Amount): Amount = Amount(value.multiply(other.value))

    fun format() = value.format()

    fun add(other: Amount) : Amount = Amount(value.add(other.value))

    fun negate(): Amount = Amount(value.negate())

    operator fun compareTo(other: Amount) : Int = value.compareTo(other.value)

    override fun toString(): String = value.format()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Amount

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }
}
