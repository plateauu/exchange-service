package dev.insidemind.bank.service

import dev.insidemind.bank.TestObjectsRepository
import dev.insidemind.bank.model.Account
import dev.insidemind.bank.model.AccountId
import dev.insidemind.bank.model.Currency
import dev.insidemind.bank.model.Pesel
import dev.insidemind.bank.model.event.CreateAccountEvent
import dev.insidemind.bank.model.repository.AccountWriteRepository
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDate

class AccountWriteServiceTest extends Specification {

    private AccountReadService readService
    private AccountWriteRepository writeRepository
    private AccountFactory accountFactory

    @Subject
    private AccountWriteService service

    void setup() {
        readService = Stub(AccountReadService)
        writeRepository = new AccountWriteRepository()
        accountFactory = new AccountFactory(Stub(CurrencyRatingService))

        service = new AccountWriteService(writeRepository, readService, accountFactory)
    }

    def 'Should throw when person is not adult'() {
        given:
        def clock = TestObjectsRepository.clock(LocalDate.of(2020, 7, 9))

        and:
        def pesel = new Pesel('02271041870', clock)

        and:
        def event = new CreateAccountEvent('test', 'test', pesel, 100.9g)

        when:
        service.createAccount(event)

        then:
        thrown(RuntimeException)
    }

    def 'Should throw when account already exists'() {
        given:
        def clock = TestObjectsRepository.clock(LocalDate.of(2020, 7, 9))
        def pesel = new Pesel('02270902958', clock)
        def event = new CreateAccountEvent('test', 'test', pesel, 100.9g)
        def account = new Account(new AccountId(pesel), event.name, event.surname, [:])

        and: 'Account exists'
        readService.findAccountFor(_ as AccountId) >> account

        when:
        service.createAccount(event)

        then:
        thrown(RuntimeException)
    }

    def 'Should save new account to database'() {
        given:
        def clock = TestObjectsRepository.clock(LocalDate.of(2020, 7, 9))
        def pesel = new Pesel('02270902958', clock)
        def accountId = new AccountId(pesel)
        def event = new CreateAccountEvent('test', 'test', pesel, 100.9g)

        and: 'Account does not exist'
        readService.findAccountFor(_ as AccountId) >> null

        when:
        service.createAccount(event)

        then: 'New account has been created'
        writeRepository.database.size() == 1

        and:
        with(writeRepository.database[accountId]) {
            it.surname == event.surname
            it.name == event.name
            it.id.pesel == event.pesel
            it.subAccounts[Currency.PLN].amount == event.amount
            it.subAccounts[Currency.USD].amount == BigDecimal.ZERO
        }
    }
}
