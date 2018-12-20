package net.yoshinorin.selfouettie.models

import java.time.LocalDateTime

case class Event(
  id: Long,
  eventType: String,
  userName: String,
  createdAt: Long
)
