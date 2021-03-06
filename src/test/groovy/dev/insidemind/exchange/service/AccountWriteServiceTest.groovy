package dev.insidemind.exchange.service

import dev.insidemind.exchange.TestObjectsRepository
import dev.insidemind.exchange.model.*
import dev.insidemind.exchange.model.event.CreateAccountEvent
import dev.insidemind.exchange.model.event.EventType
import dev.insidemind.exchange.model.repository.AccountRepository
import dev.insidemind.exchange.model.repository.AccountWriteRepository
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate

class AccountWriteServiceTest extends Specification {

    private AccountReadService readService
    private AccountWriteRepository writeRepository
    private AccountRepository repository

    @Subject
    private AccountWriteService service

    void setup() {
        repository = new AccountRepository()
        readService = Stub(AccountReadService)
        writeRepository = new AccountWriteRepository(repository)

        service = new AccountWriteService(writeRepository, readService)
    }

    def 'Should throw when person is not adult'() {
        given:
        def clock = TestObjectsRepository.clock(LocalDate.of(2020, 7, 9))

        and:
        def pesel = new Pesel('02271041870', clock)

        and:
        def event = new CreateAccountEvent('test', 'test', pesel, new Amount(100.9g))

        when:
        service.createAccount(event)

        then:
        thrown CreateAccountValidationException
    }

    def 'Should throw when account already exists'() {
        given:
        def clock = TestObjectsRepository.clock(LocalDate.of(2020, 7, 9))
        def pesel = new Pesel('02270902958', clock)
        def event = new CreateAccountEvent('test', 'test', pesel, new Amount(100.9g))
        def account = new Account(new AccountId(pesel), event.name, event.surname, [:])

        and: 'Account exists'
        readService.findAccountFor(_ as AccountId) >> account

        when:
        service.createAccount(event)

        then:
        thrown CreateAccountValidationException
    }

    def 'Should save new account to database'() {
        given:
        def clock = TestObjectsRepository.clock(LocalDate.of(2020, 7, 9))
        def pesel = new Pesel('02270902958', clock)
        def accountId = new AccountId(pesel)
        def event = new CreateAccountEvent('test', 'test', pesel, new Amount(100.9g))

        and: 'Account does not exist'
        readService.findAccountFor(_ as AccountId) >> null

        when:
        service.createAccount(event)

        then: 'New account has been created'
        repository.database.size() == 1

        and:
        with(repository.find(accountId)) {
            it.surname == event.surname
            it.name == event.name
            it.id.pesel == event.pesel
            checkSubAccount(it.subAccounts[Currency.PLN], event.amount)
            checkSubAccount(it.subAccounts[Currency.USD], Amount.ZERO)
        }

    }

    static void checkSubAccount(SubAccount subAccount, Amount amount) {
        assert subAccount.amount() == amount
        assert subAccount.operations.size() == 1
        assert subAccount.operations[0].type == EventType.CREATE
    }
}
