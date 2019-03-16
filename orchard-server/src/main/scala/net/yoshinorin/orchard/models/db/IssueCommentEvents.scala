package net.yoshinorin.orchard.models.db

import net.yoshinorin.orchard.models.BaseEvent

case class IssueCommentEvents(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  issueNumber: Int,
  action: String,
  createdAt: Long
) extends BaseEvent[IssueCommentEvents] {
  override def insert(): Unit = IssueCommentEventsRepository.insert(this)
}
