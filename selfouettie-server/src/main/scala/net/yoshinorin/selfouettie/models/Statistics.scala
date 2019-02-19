package net.yoshinorin.selfouettie.models
import net.yoshinorin.selfouettie.types.{ActionType, EventType}

case class EventStatistics(
  create: Int = 0,
  delete: Int = 0,
  fork: Int = 0,
  issueComment: Int = 0,
  issue: Int = 0,
  pullRequest: Int = 0,
  pullRequestEventReviewComment: Int = 0,
  pullRequestReview: Int = 0,
  push: Int = 0,
  release: Int = 0,
  watch: Int = 0
)

case class ContributeCount(
  repositoryId: Long,
  repositoryName: String,
  count: Long
)

