package net.yoshinorin.selfouettie.models

import java.time.LocalDateTime

case class IssueCommentEvent(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  issueNumner: Long,
  action: String,
  createdAt: Long
)
