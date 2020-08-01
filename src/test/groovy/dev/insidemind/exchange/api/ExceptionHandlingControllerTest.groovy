package dev.insidemind.exchange.api

import dev.insidemind.exchange.api.model.ExchangeRequest
import dev.insidemind.exchange.api.model.WebAmount
import dev.insidemind.exchange.model.InconsistentAccountNumberException
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException

class ExceptionHandlingControllerTest extends AbstractControllerTest {

    def 'Should return precondition failed status (412) when account number is not valid'() {
        given:
        def exchangeRequest = new ExchangeRequest('1234', new WebAmount('10'))
        def httpRequest = HttpRequest.create(HttpMethod.PUT, '/exchange/usd').body(exchangeRequest)

        when:
        client
                .toBlocking()
                .exchange(httpRequest, Argument.of(ExchangeRequest.class), Argument.of(ExceptionResponseWrapper.class))

        then:
        def exception = thrown HttpClientResponseException
        exception.status == HttpStatus.PRECONDITION_FAILED

        and:
        def body = exception.getResponse().getBody(ExceptionResponseWrapper.class)
        with(body.get()) {
            it.message
            it.type == InconsistentAccountNumberException.class.simpleName
        }
    }
}
