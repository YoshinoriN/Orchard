package net.yoshinorin.selfouettie.models

case class IssueEvent(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  issueNumber: Long,
  action: String,
  createdAt: Long
) extends BaseEvent[IssueEvent]
