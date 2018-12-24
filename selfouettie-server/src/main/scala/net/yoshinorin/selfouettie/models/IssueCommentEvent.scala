package net.yoshinorin.selfouettie.models

case class IssueCommentEvent(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  issueNumner: Long,
  action: String,
  createdAt: Long
)
