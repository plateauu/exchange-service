package dev.insidemind.bank.model

import dev.insidemind.bank.model.event.EventType
import java.time.LocalDateTime

class SubAccount(
        val currency: Currency,
        initOperation: Operation
) {
    private val operations: MutableList<Operation> = mutableListOf()

    init {
        addOperation(initOperation)
    }

    fun amount() = operations
            .map { it.amount }
            .reduce { accumulator, amount -> accumulator.add(amount) }

    @Synchronized
    fun addOperation(operation: Operation) {
        operations.add(operation)
    }
}

data class Operation(
        val amount: Amount,
        val timestamp: LocalDateTime,
        val type: EventType
)
