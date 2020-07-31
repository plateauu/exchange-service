package dev.insidemind.bank.utils

import dev.insidemind.bank.model.PolishLocale
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

fun String.parse() = AmountFormatter.parse(this)
fun BigDecimal.format() = AmountFormatter.format(this)

private object AmountFormatter {

    private val decimalFormat = DecimalFormat("#,##0.00", DecimalFormatSymbols(PolishLocale.value))
    fun parse(amount: String) : BigDecimal {
        val value: Number = decimalFormat.parse(amount)
        return BigDecimal(value.toString()).setScale(2, RoundingMode.HALF_UP)
    }

    fun format(amount: BigDecimal): String =
            decimalFormat.format(amount)
}
