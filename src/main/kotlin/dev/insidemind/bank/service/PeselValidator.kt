package dev.insidemind.bank.service

import dev.insidemind.bank.model.Pesel

object PeselValidator {
    private const val PESEL_LENGHT = 11

    fun validate(pesel: Pesel) {

        val array = pesel.value
                .toCharArray()
                .map { it.toString().toInt() }

        if (array.size != PESEL_LENGHT) throw RuntimeException("Pesel number is too short")

        var sum = 0
        for (i in 0..9) {
            sum += array[i] * getMultiplier(i + 1)
        }

        val modulo = sum % 10
        val lastDigit = array.last()

        if (modulo == 0) {
            if (lastDigit != 0)
                throw RuntimeException("Incorrect Pesel Number. Last number should be equal to 0 instead $lastDigit")
        } else {
            val calculatedLastDigit = 10 - modulo
            if (calculatedLastDigit != lastDigit)
                throw RuntimeException("Incorrect Pesel Number. Last number should be equal to $calculatedLastDigit instead $lastDigit")
        }
    }

    private fun getMultiplier(index: Int): Int =
            when (index % 4) {
                1 -> 1
                2 -> 3
                3 -> 7
                0 -> 9
                else -> throw RuntimeException("Consistency error during a pesel index calculation")
            }
}
