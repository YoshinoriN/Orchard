package net.yoshinorin.selfouettie.models

import java.time.LocalDateTime

case class IssueEvents(
  eventId: Long,
  repositoryId: Long,
  issueNumner: Long,
  action: String,
  createdAt: LocalDateTime
)
