package net.yoshinorin.selfouettie.models

import java.time.LocalDateTime

case class IssueEvent(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  issueNumner: Long,
  action: String,
  createdAt: Long
)
