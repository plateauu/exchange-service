package dev.insidemind.bank.model

import dev.insidemind.bank.service.PeselValidator
import java.time.LocalDate

class Pesel(val value: String) {

    fun validate() {
        PeselValidator.validate(this)
    }

    fun isLegalAge(): Boolean {
        return true
    }

    fun getBirthDate(): LocalDate {
        val birthDateArray = value.substring(0, 6)

        val (monthEvaluated, century) = evaluateCentury(birthDateArray[2].toString().toInt())

        val year = century + birthDateArray.substring(0, 2).toInt()
        val month = monthEvaluated.toString() + birthDateArray[3].toString()
        val day = birthDateArray.substring(4, 6)

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

private typealias Month = Int
private typealias Century = Int
