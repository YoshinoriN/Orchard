package net.yoshinorin.selfouettie.models

import java.time.LocalDateTime

case class PushEvent(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  ref: String,
  size: Int,
  createdAt: Long
)
