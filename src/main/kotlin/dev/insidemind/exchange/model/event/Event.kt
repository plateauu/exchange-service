package dev.insidemind.exchange.model.event

import dev.insidemind.exchange.model.Amount
import java.time.LocalDateTime

abstract class Event(val type: EventType, val amount: Amount){
    val effectiveDate: LocalDateTime = LocalDateTime.now()
    fun isZero() = amount.isZero()
}
