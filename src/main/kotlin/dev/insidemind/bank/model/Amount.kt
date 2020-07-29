package dev.insidemind.bank.model

import dev.insidemind.bank.utils.format
import java.math.BigDecimal

inline class Amount(private val value: BigDecimal) {
    override fun toString() = value.format()
}
