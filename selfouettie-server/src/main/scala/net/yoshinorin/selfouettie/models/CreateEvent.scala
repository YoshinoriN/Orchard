package net.yoshinorin.selfouettie.models

import java.time.LocalDateTime

case class CreateEvent(
  eventId: Long,
  refType: String,
  ref: String,
  createdAt: Long
)
