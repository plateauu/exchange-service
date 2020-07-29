package dev.insidemind.bank.utils

import dev.insidemind.bank.utils.AmountFormatter
import spock.lang.Specification
import spock.lang.Unroll

class AmountFormatterTest extends Specification {

    @Unroll
    def "Should parse polish zloty #toParse to decimal format"() {
        expect:
        AmountFormatter.INSTANCE.parse(toParse) == result

        where:
        toParse    || result
        '123,2333' || 123.23g
        '123,2343' || 123.23g
        '123,2353' || 123.24g
        '123,2363' || 123.24g
        '123,2373' || 123.24g
        '123,2'    || 123.20g
    }

    @Unroll
    def "Should format decimal #input to polish zloty format"() {
        expect:
        AmountFormatter.INSTANCE.format(input) == result

        where:
        input   || result
        123.23g || '123,23'
        123.24g || '123,24'
        123.20g || '123,20'
        123.2g  || '123,20'
    }

}
