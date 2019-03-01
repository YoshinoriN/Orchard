package net.yoshinorin.orchard.models
import net.yoshinorin.orchard.types.{ActionType, EventType}

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

class ContributedRepository(
  val repositoryId: Long,
  val repositoryName: String,
  val url: String,
  val count: Long
)

object ContributedRepository {
  def apply(repositoryId: Long, repositoryName: String, url: String, count: Long): ContributedRepository = {
    new ContributedRepository(repositoryId, repositoryName, "https://github.com/" + url, count)
  }
}
