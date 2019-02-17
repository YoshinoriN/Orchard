package net.yoshinorin.selfouettie.models
import net.yoshinorin.selfouettie.types.{ActionType, EventType}

case class EventStatistics(
  Create: Int = 0,
  Delete: Int = 0,
  Fork: Int = 0,
  IssueComment: Int = 0,
  Issue: Int = 0,
  PullRequest: Int = 0,
  PullRequestEventReviewComment: Int = 0,
  PullRequestReview: Int = 0,
  Push: Int = 0,
  Release: Int = 0,
  Watch: Int = 0
)
