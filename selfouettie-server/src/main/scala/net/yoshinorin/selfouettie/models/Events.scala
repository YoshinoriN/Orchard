package net.yoshinorin.selfouettie.models

import java.time.LocalDateTime

case class Events(
  id: Long,
  eventType: String,
  userName: String,
  createdAt: LocalDateTime
)
