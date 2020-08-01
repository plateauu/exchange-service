package dev.insidemind.exchange.api

import dev.insidemind.exchange.model.Currency
import io.micronaut.http.HttpStatus

class ExchangeControllerTest extends AbstractControllerTest {

    void setup() {
        createAccount()
    }

    def 'Should exchange money to USD currency when usd endpoint is called'() {
        when:
        def response = exchange('/exchange/usd')

        then:
        response.status == HttpStatus.OK

        and:
        with(response.body.get()) {
            it.accountId == PESEL
            it.subAccounts[Currency.PLN] == "80,00 PLN"
            it.subAccounts[Currency.USD] == '10,00 USD'
            it.operationDate
        }
    }

    def 'Should exchange money to PLN currency when pln endpoint is called'() {
        given: 'Some USD has been bought'
        exchange('/exchange/usd')

        when:
        def response = exchange('/exchange/pln')

        then:
        response.status == HttpStatus.OK

        and:
        with(response.body.get()) {
            it.accountId == PESEL
            it.subAccounts[Currency.PLN] == "90,00 PLN"
            it.subAccounts[Currency.USD] == '5,00 USD'
            it.operationDate
        }
    }


}
