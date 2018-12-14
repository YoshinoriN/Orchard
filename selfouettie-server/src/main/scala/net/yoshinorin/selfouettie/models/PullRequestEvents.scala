package net.yoshinorin.selfouettie.models

import java.time.LocalDateTime

case class PullRequestEvents(
  eventId: Long,
  repositoryId: Long,
  pullRequestNumber: Long,
  action: String,
  createdAt: LocalDateTime
)
