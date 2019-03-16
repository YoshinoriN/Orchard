package net.yoshinorin.orchard.models.db

import net.yoshinorin.orchard.models.BaseEvent

case class IssueEvents(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  issueNumber: Int,
  action: String,
  createdAt: Long
) extends BaseEvent[IssueEvents] {
  override def insert(): Unit = IssuesEventsRepository.insert(this)
}
