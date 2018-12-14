package net.yoshinorin.selfouettie.models

import java.time.LocalDateTime

case class IssueCommentEvents(
  eventId: Long,
  repositoryId: Long,
  issueNumner: Long,
  action: String,
  createdAt: LocalDateTime
)
