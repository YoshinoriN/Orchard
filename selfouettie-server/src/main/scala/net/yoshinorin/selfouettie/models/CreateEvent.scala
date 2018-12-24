package net.yoshinorin.selfouettie.models

import java.time.LocalDateTime

case class CreateEvent(
  eventId: Long,
  userName: String,
  refType: String,
  ref: String,
  createdAt: Long
)
