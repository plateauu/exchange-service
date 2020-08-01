package dev.insidemind.exchange.model

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

    fun isSufficientAmount(amount: Amount) = amount() >= amount

    @Synchronized
    fun addOperation(operation: Operation) {
        operations.add(operation)
    }
}

