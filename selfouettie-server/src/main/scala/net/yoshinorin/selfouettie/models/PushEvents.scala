package net.yoshinorin.selfouettie.models

import java.time.LocalDateTime

case class PushEvents(
  eventId: Long,
  repositoryId: Long,
  ref: String,
  size: Int,
  createdAt: LocalDateTime
)
