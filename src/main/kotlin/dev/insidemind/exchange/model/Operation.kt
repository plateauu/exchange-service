package dev.insidemind.exchange.model

import dev.insidemind.exchange.model.event.EventType
import java.time.LocalDateTime

data class Operation(
        val amount: Amount,
        val effectiveDate: LocalDateTime,
        val type: EventType,
        val direction: Direction
) {
    companion object {
        fun income(amount: Amount, effectiveDate: LocalDateTime, type: EventType) =
                Operation(amount, effectiveDate, type, Direction.INCOME)

        fun outcome(amount: Amount, effectiveDate: LocalDateTime, type: EventType) =
                Operation(amount.negate(), effectiveDate, type, Direction.OUTCOME)
    }
}
