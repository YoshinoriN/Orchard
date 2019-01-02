package net.yoshinorin.selfouettie.models.db

import net.yoshinorin.selfouettie.models.BaseEvent

case class IssueEvents(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  issueNumber: Int,
  action: String,
  createdAt: Long
) extends BaseEvent[IssueEvents]
