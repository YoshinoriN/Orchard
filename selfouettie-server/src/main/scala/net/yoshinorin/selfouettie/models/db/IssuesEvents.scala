package net.yoshinorin.selfouettie.models.db

import net.yoshinorin.selfouettie.models.BaseEvent

case class IssuesEvents(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  issueNumber: Long,
  action: String,
  createdAt: Long
) extends BaseEvent[IssuesEvents]
