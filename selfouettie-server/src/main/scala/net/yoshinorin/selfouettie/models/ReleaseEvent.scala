package net.yoshinorin.selfouettie.models

import java.time.LocalDateTime

case class ReleaseEvent(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  tagName: String,
  name: String,
  action: String,
  createdAt: Long
)
