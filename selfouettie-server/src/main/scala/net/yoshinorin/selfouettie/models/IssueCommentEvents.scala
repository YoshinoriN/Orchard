package net.yoshinorin.selfouettie.models

case class IssueCommentEvents(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  issueNumber: Long,
  action: String,
  createdAt: Long
) extends BaseEvent[IssueCommentEvents]
