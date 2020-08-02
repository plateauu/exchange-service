package dev.insidemind.exchange.service


import dev.insidemind.exchange.model.*
import dev.insidemind.exchange.model.event.EventType
import dev.insidemind.exchange.model.event.ExchangeEvent
import dev.insidemind.exchange.service.rating.CurrencyRating
import dev.insidemind.exchange.service.rating.CurrencyRatingService
import spock.lang.Specification
import spock.lang.Subject

import static dev.insidemind.exchange.TestObjectsRepository.accountWithPlnCreated
import static dev.insidemind.exchange.TestObjectsRepository.accountWithUsdCreated
import static dev.insidemind.exchange.model.Currency.PLN
import static dev.insidemind.exchange.model.Currency.USD
import static dev.insidemind.exchange.model.event.EventType.CREATE
import static dev.insidemind.exchange.model.event.EventType.EXCHANGE

class ExchangeServiceTest extends Specification {

    private AccountWriteService writeService
    private AccountReadService readService
    private CurrencyRatingService ratingService

    private Amount initialAmount

    @Subject
    private ExchangeService service

    void setup() {
        writeService = Stub(AccountWriteService) {
            save(_ as Account) >> { Account account -> account }
        }

        initialAmount = new Amount(10.0g)

        ratingService = Stub(CurrencyRatingService) {
            getCurrentUsdToPlnRating() >> new CurrencyRating(2.0g)
            getCurrentPlnToUsdRating() >> new CurrencyRating(0.5g)
        }
    }

    def 'Should exchange money from PLN to USD'() {
        given:
        def savedAccount = accountWithPlnCreated(initialAmount)
        readService = Stub(AccountReadService) {
            findAccountForOrThrow(_ as AccountId) >> savedAccount
        }
        service = new ExchangeService(readService, writeService, ratingService)

        and:
        def event = new ExchangeEvent(new AccountId('92050602479'), USD, PLN, new Amount(2.0g))

        when:
        def response = service.exchange(event)

        then:
        response

        and:
        response.operationDate
        response.id == savedAccount.id

        and: 'Should create new exchange operation with outcome direction'
        with(response.subAccounts[PLN]) {
            amount() == new Amount(6.0g)
            operations.size() == 2
            Map<EventType, Operation> operations = operations.collectEntries { operation -> [(operation.type): operation] }
            operations[CREATE].amount == initialAmount
            operations[EXCHANGE].amount == new Amount(-4.0g)
            operations[EXCHANGE].direction == Direction.OUTCOME
        }

        and: 'Should create new exchange operation with income direction'
        with(response.subAccounts[USD]) {
            amount() == new Amount(2.0g)
            operations.size() == 2
            Map<EventType, Operation> operations = operations.collectEntries { operation -> [(operation.type): operation] }
            operations[CREATE].amount == Amount.ZERO
            operations[EXCHANGE].amount == new Amount(2.0g)
            operations[EXCHANGE].direction == Direction.INCOME
        }
    }

    def 'Should exchange money from USD to PLN'() {
        given:
        def savedAccount = accountWithUsdCreated(initialAmount)
        readService = Stub(AccountReadService) {
            findAccountForOrThrow(_ as AccountId) >> savedAccount
        }
        service = new ExchangeService(readService, writeService, ratingService)

        and:
        def event = new ExchangeEvent(new AccountId('92050602479'), PLN, USD, new Amount(4.0g))

        when:
        def response = service.exchange(event)

        then:
        response

        and:
        response.operationDate
        response.id == savedAccount.id

        and: 'Should create new exchange operation with income direction'
        with(response.subAccounts[USD]) {
            amount() == new Amount(8.0g)
            operations.size() == 2
            Map<EventType, Operation> operations = operations.collectEntries { operation -> [(operation.type): operation] }
            operations[CREATE].amount == initialAmount
            operations[EXCHANGE].amount == new Amount(-2.0g)
            operations[EXCHANGE].direction == Direction.OUTCOME
        }

        and: 'Should create new exchange operation with income direction'
        with(response.subAccounts[PLN]) {
            amount() == new Amount(4.0g)
            operations.size() == 2
            Map<EventType, Operation> operations = operations.collectEntries { operation -> [(operation.type): operation] }
            operations[CREATE].amount == Amount.ZERO
            operations[EXCHANGE].amount == new Amount(4.0g)
            operations[EXCHANGE].direction == Direction.INCOME
        }
    }

    def 'Should throw when there is not sufficient amount money to make exchange at source account'() {
        def savedAccount = accountWithPlnCreated(initialAmount)
        readService = Stub(AccountReadService) {
            findAccountForOrThrow(_ as AccountId) >> savedAccount
        }
        service = new ExchangeService(readService, writeService, ratingService)

        def event = new ExchangeEvent(new AccountId('92050602479'), USD, PLN, new Amount(10.0g))

        when:
        service.exchange(event)

        then:
        thrown InsufficientSubAccountAmountException
    }

}
