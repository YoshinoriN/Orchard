package net.yoshinorin.orchard.models.db

import net.yoshinorin.orchard.models.BaseEvent

case class PullRequestReviewCommentEvents(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  pullRequestNumber: Int,
  action: String,
  createdAt: Long
) extends BaseEvent[PullRequestReviewCommentEvents] {
  override def insert(): Unit = PullRequestReviewCommentEventsRepository.insert(this)
}
