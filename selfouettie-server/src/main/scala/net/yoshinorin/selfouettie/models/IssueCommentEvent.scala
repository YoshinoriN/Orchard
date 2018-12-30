package net.yoshinorin.selfouettie.models

case class IssueCommentEvent(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  issueNumber: Long,
  action: String,
  createdAt: Long
) extends BaseEvent[IssueCommentEvent]
