package net.yoshinorin.orchard.models.db

import net.yoshinorin.orchard.models.BaseEvent

case class PullRequestEvents(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  pullRequestNumber: Int,
  action: String,
  createdAt: Long
) extends BaseEvent[PullRequestEvents] {
  override def insert(): Unit = PullRequestEventsRepository.insert(this)
}
