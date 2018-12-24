package net.yoshinorin.selfouettie.models

import java.time.LocalDateTime

case class WatchEvent(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  action: String,
  createdAt: Long
)
