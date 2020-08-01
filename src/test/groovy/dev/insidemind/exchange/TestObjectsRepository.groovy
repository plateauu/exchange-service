package dev.insidemind.exchange

import java.time.*

class TestObjectsRepository {

    static clock(LocalDate date) {
        def systemZoneId = ZoneId.systemDefault()
        def instant = ZonedDateTime.of(date, LocalTime.of(0, 0, 0, 0), systemZoneId).toInstant()
        return Clock.fixed(instant, systemZoneId)
    }

}
