package net.yoshinorin.selfouettie.models

import java.time.LocalDateTime

case class PullRequestEvent(
  eventId: Long,
  repositoryId: Long,
  pullRequestNumber: Long,
  action: String,
  createdAt: Long
)
