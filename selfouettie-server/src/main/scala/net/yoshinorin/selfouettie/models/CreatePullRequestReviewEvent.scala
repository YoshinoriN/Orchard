package net.yoshinorin.selfouettie.models

import java.time.LocalDateTime

case class CreatePullRequestReviewEvent(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  pullRequestNumber: Long,
  action: String,
  createdAt: Long
)
