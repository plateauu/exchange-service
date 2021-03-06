package dev.insidemind.exchange.service

import dev.insidemind.exchange.model.Pesel
import dev.insidemind.exchange.model.PeselValidationException
import spock.lang.Specification
import spock.lang.Unroll

class PeselValidatorTest extends Specification {

    @Unroll
    def 'Should validate correct pesel number #pesel without errors'() {
        when:
        PeselValidator.INSTANCE.validate(new Pesel(pesel))

        then:
        noExceptionThrown()

        where:
        pesel << ['24121845836', '81082937790', '02270103571']
    }

    @Unroll
    def "Should throw when pesel number #pesel is incorrect"() {
        when:
        PeselValidator.INSTANCE.validate(new Pesel(pesel))

        then:
        thrown PeselValidationException

        where:
        pesel << ['24121845835', '81082917790', '20270103571', '08291779', '2027010357123432', '23454A']
    }
}
