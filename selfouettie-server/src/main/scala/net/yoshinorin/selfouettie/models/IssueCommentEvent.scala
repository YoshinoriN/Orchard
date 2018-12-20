package net.yoshinorin.selfouettie.models

import java.time.LocalDateTime

case class IssueCommentEvent(
  eventId: Long,
  repositoryId: Long,
  issueNumner: Long,
  action: String,
  createdAt: Long
)
