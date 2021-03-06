package dev.insidemind.exchange.service

import dev.insidemind.exchange.model.Pesel
import dev.insidemind.exchange.model.PeselValidationException
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object PeselValidator {
    private const val PESEL_LENGHT = 11

    private val logger: Logger = LoggerFactory.getLogger(Pesel::class.java)

    fun validate(pesel: Pesel) {

        val peselArray = pesel.value.toCharArray()

        isAllDigits(peselArray, pesel)

        val peselIntArray = peselArray.map { it.toString().toInt() }

        if (peselIntArray.size != PESEL_LENGHT)
            throw PeselValidationException("Pesel number [$pesel] has wrong number of digits")

        var sum = 0
        for (i in 0..9) {
            sum += peselIntArray[i] * getMultiplier(i + 1)
        }

        isCheckNumberCorrect(sum, peselIntArray)
    }

    private fun isAllDigits(peselArray: CharArray, pesel: Pesel) {
        peselArray.forEach {
            if (!it.isDigit()) throw PeselValidationException("Wrong pesel: $pesel. Pesel number may contain only digits.")
        }
        logger.debug("Validation: All digits - positive")
    }

    private fun isCheckNumberCorrect(
            sum: Int,
            pesel: List<Int>
    ) {
        val modulo = sum % 10
        val lastDigit = pesel.last()

        if (modulo == 0) {
            if (lastDigit != 0)
                throw PeselValidationException("Incorrect Pesel Number. Last number should be equal to 0 instead $lastDigit")
        } else {
            val calculatedLastDigit = 10 - modulo
            if (calculatedLastDigit != lastDigit)
                throw PeselValidationException("Incorrect Pesel Number. Last number should be equal to $calculatedLastDigit instead $lastDigit")
        }
        logger.debug("Validation: CheckNumber - positive")
    }

    private fun getMultiplier(index: Int): Int =
            when (index % 4) {
                1 -> 1
                2 -> 3
                3 -> 7
                0 -> 9
                else -> throw PeselValidationException("Consistency error during a pesel index calculation")
            }
}
