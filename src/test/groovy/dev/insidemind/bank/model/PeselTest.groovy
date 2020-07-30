package dev.insidemind.bank.model

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

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

}
