package net.yoshinorin.selfouettie.models

import java.time.LocalDateTime

case class WatchEvent(
  eventId: Long,
  repositoryId: Long,
  action: String,
  createdAt: LocalDateTime
)
