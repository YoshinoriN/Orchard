package net.yoshinorin.orchard.utils

import java.time.{Instant, ZoneOffset, ZonedDateTime}

object Converter {

  implicit class zonedDateTimeConverter(l: Long) {
    def toZonedDateTime: ZonedDateTime = {
      ZonedDateTime.ofInstant(Instant.ofEpochSecond(l), ZoneOffset.UTC)
    }
  }

}
