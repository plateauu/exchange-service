package dev.insidemind.bank.model

import dev.insidemind.bank.service.PeselValidator
import io.micronaut.core.util.StringUtils
import java.time.LocalDate

class Pesel(val value: String) {

    fun validate() {
        PeselValidator.validate(this)
    }

    fun isLegalAge(): Boolean {
        return true
    }

    fun getBirthDate(): LocalDate {
        val birthArray = value.toCharArray()
                .take(6)
                .map { it.toString() }

        val (monthEvaluated, century) = evaluateCentury(birthArray[2].toInt())

        val year = century + birthArray.joinToStringWithoutSeparator(0, 2).toInt()
        val month = monthEvaluated.toString() + birthArray[3]
        val day = birthArray.joinToStringWithoutSeparator(4, 6)

        return LocalDate.of(year, month.toInt(), day.toInt())
    }

    private fun evaluateCentury(monthNumber: Int): Pair<Month, Century> =
            when (monthNumber) {
                0, 1 -> monthNumber to 1900
                2, 3 -> monthNumber - 2 to 2000
                4, 5 -> monthNumber - 4 to 2100
                6, 7 -> monthNumber - 6 to 2200
                8, 9 -> monthNumber - 8 to 1800
                else -> throw RuntimeException("Inconsistent pesel number. Unable to find birth date at pesel $value")
            }
}

private fun List<String>.joinToStringWithoutSeparator(fromIndex: Int, toIndex: Int) =
        this.subList(fromIndex, toIndex).joinToString(separator = StringUtils.EMPTY_STRING)

private typealias Month = Int
private typealias Century = Int
