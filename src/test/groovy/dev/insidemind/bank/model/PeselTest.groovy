package dev.insidemind.bank.model

import spock.lang.Specification
import spock.lang.Unroll

import java.time.Clock
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

class PeselTest extends Specification {

    @Unroll
    def "Should return date of birth parsed from pesel number #input"() {
        given:
        def pesel = new Pesel(input)

        when:
        def date = pesel.getBirthDate()

        then:
        date == expectedBirthDate

        where:
        input         || expectedBirthDate
        '02270103571' || LocalDate.of(2002, 7, 1)
        '96070990598' || LocalDate.of(1996, 7, 9)
    }

    @Unroll
    def "Should check if pesel #input belongs to adult person"() {
        given:
        def systemZoneId = ZoneId.systemDefault()
        def instant = ZonedDateTime.of(2020, 7, 9, 0, 0, 0, 0, systemZoneId).toInstant()
        def testClock = Clock.fixed(instant, systemZoneId)

        and:
        def pesel = new Pesel(input, testClock)

        expect:
        pesel.isAdult() == result

        where:
        input         || result
        '96070990598' || true
        '02270902958' || true
        '02271041870' || false
    }

}
