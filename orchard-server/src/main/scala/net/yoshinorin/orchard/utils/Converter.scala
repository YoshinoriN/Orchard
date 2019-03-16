package net.yoshinorin.orchard.utils

import java.time.{Instant, ZoneId, ZoneOffset, ZonedDateTime}
import net.yoshinorin.orchard.definitions.event.EventType

object Converter {

  implicit class zonedDateTimeConverter(l: Long) {
    def toZonedDateTime: ZonedDateTime = {
      ZonedDateTime.ofInstant(Instant.ofEpochSecond(l), ZoneOffset.UTC)
    }
  }

}
