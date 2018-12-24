package net.yoshinorin.selfouettie.models

case class PullRequestReviewCommentEvent(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  pullRequestNumber: Long,
  action: String,
  createdAt: Long
) extends BaseEvent[PullRequestReviewCommentEvent]
