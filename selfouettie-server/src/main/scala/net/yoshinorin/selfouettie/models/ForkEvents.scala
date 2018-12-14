package net.yoshinorin.selfouettie.models

import java.time.LocalDateTime

case class ForkEvents(
  eventId: Long,
  forkRepositoryId: Long,
  createdAt: LocalDateTime
)
