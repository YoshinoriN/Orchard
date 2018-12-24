package net.yoshinorin.selfouettie.models

import java.time.LocalDateTime

case class Event(
  id: Long,
  eventType: String,
  userName: String,
  repositoryId: Long,
  action: String,
  url: String,
  createdAt: Long
)
