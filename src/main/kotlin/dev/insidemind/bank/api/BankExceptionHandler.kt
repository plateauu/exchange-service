package dev.insidemind.bank.api

import dev.insidemind.bank.model.*
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import javax.inject.Singleton

@Singleton
@Produces
@Requires(classes = [ExceptionHandler::class, BankServiceException::class])
class BattleShipExceptionHandler : ExceptionHandler<BankServiceException, HttpResponse<ExceptionResponseWrapper>> {

    override fun handle(request: HttpRequest<Any>, exception: BankServiceException): HttpResponse<ExceptionResponseWrapper> {
        return HttpResponse
                .status<ExceptionResponseWrapper>(evaluateStatusCode(exception))
                .body(ExceptionResponseWrapper(exception::class.simpleName!!, exception.message!!))
    }

    private fun evaluateStatusCode(exception: BankServiceException): HttpStatus =
        when (exception) {
            is CreateAccountValidationException -> HttpStatus.UNPROCESSABLE_ENTITY
            is AccountNotFountException -> HttpStatus.UNPROCESSABLE_ENTITY
            is PeselValidationException -> HttpStatus.PRECONDITION_FAILED
            is InconsistentAccountNumberException -> HttpStatus.PRECONDITION_FAILED
            is InsufficientSubAccountAmountException -> HttpStatus.PRECONDITION_FAILED
            is NbpApiClientException -> HttpStatus.INTERNAL_SERVER_ERROR
    }
}

data class ExceptionResponseWrapper (val type: String, val message: String)
