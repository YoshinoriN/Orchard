package net.yoshinorin.selfouettie.models

case class PullRequestReviewCommentEvents(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  pullRequestNumber: Long,
  action: String,
  createdAt: Long
) extends BaseEvent[PullRequestReviewCommentEvents]
