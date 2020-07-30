package dev.insidemind.bank.model

import dev.insidemind.bank.service.PeselValidator

class Pesel(val value: String) {

    fun validate() {
        PeselValidator.validate(this)
    }

    fun isMultiAge() {

    }

}
