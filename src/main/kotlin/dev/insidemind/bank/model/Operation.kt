package dev.insidemind.bank.model

import dev.insidemind.bank.model.event.EventType
import java.time.LocalDateTime

data class Operation(
        val amount: Amount,
        val timestamp: LocalDateTime,
        val type: EventType,
        val direction: Direction
) {
    companion object {
        fun income(amount: Amount, timestamp: LocalDateTime, type: EventType) = Operation(amount, timestamp, type, Direction.INCOME)
        fun outcome(amount: Amount, timestamp: LocalDateTime, type: EventType) = Operation(amount, timestamp, type, Direction.OUTCOME)
    }
}
