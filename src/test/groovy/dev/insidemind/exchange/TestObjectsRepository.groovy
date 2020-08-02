package dev.insidemind.exchange

import dev.insidemind.exchange.model.*

import java.time.*

import static dev.insidemind.exchange.model.Currency.PLN
import static dev.insidemind.exchange.model.Currency.USD
import static dev.insidemind.exchange.model.Direction.INCOME
import static dev.insidemind.exchange.model.event.EventType.CREATE

class TestObjectsRepository {

    static clock(LocalDate date) {
        def systemZoneId = ZoneId.systemDefault()
        def instant = ZonedDateTime.of(date, LocalTime.of(0, 0, 0, 0), systemZoneId).toInstant()
        return Clock.fixed(instant, systemZoneId)
    }

    static accountWithPlnCreated(Amount amount) {
        def id = new AccountId('92050602479')
        new Account(id, 'test', 'surname', [:])
                .tap {
                    it.subAccounts[PLN] = new SubAccount(PLN, income(amount))
                    it.subAccounts[USD] = new SubAccount(USD, income(Amount.ZERO))
                }
    }

    static accountWithUsdCreated(Amount amount) {
        def id = new AccountId('92050602479')
        new Account(id, 'test', 'surname', [:])
                .tap {
                    it.subAccounts[USD] = new SubAccount(USD, income(amount))
                    it.subAccounts[PLN] = new SubAccount(PLN, income(Amount.ZERO))
                }
    }

    private static Operation income(Amount amount) {
        return new Operation(amount, LocalDateTime.now(), CREATE, INCOME)
    }

}
