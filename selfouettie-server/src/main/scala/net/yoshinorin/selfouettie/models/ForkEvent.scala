package net.yoshinorin.selfouettie.models

import java.time.LocalDateTime

case class ForkEvent(
  eventId: Long,
  userName: String,
  forkRepositoryId: Long,
  createdAt: Long
)
