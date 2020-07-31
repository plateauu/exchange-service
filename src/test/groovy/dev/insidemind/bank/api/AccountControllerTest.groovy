package dev.insidemind.bank.api

import dev.insidemind.bank.api.model.CreateAccountRequest
import dev.insidemind.bank.api.model.WebAmount
import dev.insidemind.bank.model.Currency
import dev.insidemind.bank.model.repository.AccountRepository
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
    private HttpClient client

    @Inject
    private AccountRepository repository

    void setup(){
        repository.database.clear()
    }

    def 'Should create account when new account endpoint is called'() {
        given:
        def request = new CreateAccountRequest(NAME, SURE_NAME, PESEL, AMOUNT)

        when:
        def response = client
                .toBlocking()
                .exchange(HttpRequest.create(HttpMethod.POST, "/account").body(request), TestResponse.class)

        then:
        response.status == HttpStatus.OK

        and: 'Sub accounts should be created'
        with(response.body.get()) {
            it.accountId == PESEL
            it.subAccounts[Currency.PLN] == "$AMOUNT PLN"
            it.subAccounts[Currency.USD] == '0,00 USD'
        }
    }

    def 'Should get account balance'() {
        given: 'Account is created'
        def request = new CreateAccountRequest(NAME, SURE_NAME, PESEL, AMOUNT)
        client
                .toBlocking()
                .exchange(HttpRequest.create(HttpMethod.POST, '/account').body(request), TestResponse.class)

        when: 'Get request is invoked'
        def response = client
                .toBlocking()
                .exchange("/account/$PESEL", TestResponse.class)

        then:
        response.status == HttpStatus.OK

        and:
        with(response.body.get()) {
            it.accountId == PESEL
            it.subAccounts[Currency.PLN] == "$AMOUNT PLN"
            it.subAccounts[Currency.USD] == '0,00 USD'
        }
    }

    static class TestResponse {
        String accountId
        Map<Currency, String> subAccounts
    }
}
