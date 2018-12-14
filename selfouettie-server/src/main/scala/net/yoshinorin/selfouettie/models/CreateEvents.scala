package net.yoshinorin.selfouettie.models

import java.time.LocalDateTime

case class CreateEvents(
  eventId: Long,
  refType: String,
  ref: String,
  createdAt: LocalDateTime
)
