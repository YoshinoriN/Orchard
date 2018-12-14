package net.yoshinorin.selfouettie.models

import java.time.LocalDateTime

case class ForkEvent(
  eventId: Long,
  forkRepositoryId: Long,
  createdAt: LocalDateTime
)
