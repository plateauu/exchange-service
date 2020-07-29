package dev.insidemind.bank.api

import dev.insidemind.bank.api.model.CreateAccountRequest
import dev.insidemind.bank.api.model.WebAmount
import dev.insidemind.bank.model.Currency
import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class AccountControllerTest extends Specification {

    private static final String NAME = 'testName'
    private static final String SURE_NAME = 'testSurname'
    private static final String PESEL = '42052061347'
    private static final WebAmount AMOUNT = new WebAmount("123,23")

    @Inject
    @Client("/")
    protected HttpClient client

    def 'Should create account when new account endpoint is called'() {
        given:
        def request = new CreateAccountRequest(NAME, SURE_NAME, PESEL, AMOUNT)

        def exchange = client
                .toBlocking()
                .exchange(HttpRequest.create(HttpMethod.POST, "/account").body(request), Response.class)
        when:
        def response = exchange

        then:
        response.status == HttpStatus.OK

        and:
        with(response.body.get()){
            it.accountId == PESEL
            it.balances[Currency.PLN] == "$AMOUNT PLN"
            it.balances[Currency.USD] == '0,00 USD'
        }
    }

    static class Response {
        String accountId
        Map<Currency, String> balances
    }

}
