package dev.insidemind.bank.api

import dev.insidemind.bank.model.Currency
import io.micronaut.http.HttpStatus

class AccountControllerTest extends AbstractControllerTest {

    def 'Should create account when new account endpoint is called'() {
        when:
        def response = createAccount()

        then:
        response.status == HttpStatus.OK

        and: 'SubAccounts should be created'
        with(response.body.get()) {
            it.accountId == PESEL
            it.subAccounts[Currency.PLN] == "$AMOUNT PLN"
            it.subAccounts[Currency.USD] == '0,00 USD'
        }
    }

    def 'Should get account balance'() {
        given: 'Account is already created'
        createAccount()

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

}
