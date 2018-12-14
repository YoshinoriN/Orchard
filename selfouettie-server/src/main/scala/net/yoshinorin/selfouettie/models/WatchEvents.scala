package net.yoshinorin.selfouettie.models

import java.time.LocalDateTime

case class WatchEvents(
  eventId: Long,
  repositoryId: Long,
  action: String,
  createdAt: LocalDateTime
)
