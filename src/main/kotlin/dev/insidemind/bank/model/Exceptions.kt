package dev.insidemind.bank.model

sealed class BankServiceException(message: String) : RuntimeException(message)
class CreateAccountValidationException(message: String) : BankServiceException(message)
class PeselValidationException(message: String) : BankServiceException(message)
