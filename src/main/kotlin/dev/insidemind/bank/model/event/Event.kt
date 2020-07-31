package dev.insidemind.bank.model.event

import dev.insidemind.bank.model.Amount
import java.time.LocalDateTime

abstract class Event(val type: EventType, val amount: Amount){
    val timestamp: LocalDateTime = LocalDateTime.now()
    fun isZero() = amount.isZero()
}
