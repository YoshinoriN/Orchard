package net.yoshinorin.selfouettie.models

import java.time.LocalDateTime

case class CreatePullRequestReviewEvent(
  eventId: Long,
  repositoryId: Long,
  pullRequestNumber: Long,
  action: String,
  createdAt: LocalDateTime
)
