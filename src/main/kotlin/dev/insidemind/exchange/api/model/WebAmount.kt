package dev.insidemind.exchange.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import dev.insidemind.exchange.model.Amount
import dev.insidemind.exchange.utils.format
import dev.insidemind.exchange.utils.parse
import java.math.BigDecimal

class WebAmount(@JsonProperty amount: String) {
    constructor(amount: Amount) : this(amount.format())

    val value: BigDecimal = amount.parse()

    @JsonValue
    override fun toString() = value.format()
}
