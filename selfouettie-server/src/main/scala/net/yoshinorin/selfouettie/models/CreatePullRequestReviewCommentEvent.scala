package net.yoshinorin.selfouettie.models

case class CreatePullRequestReviewCommentEvent(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  pullRequestNumber: Long,
  action: String,
  createdAt: Long
)
