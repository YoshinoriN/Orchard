package net.yoshinorin.selfouettie.models.db

import net.yoshinorin.selfouettie.services.QuillProvider
import net.yoshinorin.selfouettie.utils.Logger

trait PullRequestEventRepository {

  def insert(pullRequestEvent: PullRequestEvents): Unit
  def findById(eventId: Long): Option[PullRequestEvents]

}

object PullRequestEventRepository extends PullRequestEventRepository with QuillProvider with Logger {

  import ctx._;

  /**
   * Insert PullRequest
   *
   * @param pullRequestEvent PullRequestEvents case class
   */
  def insert(pullRequestEvent: PullRequestEvents): Unit = {
    this.findById(pullRequestEvent.eventId) match {
      case None => run(query[PullRequestEvents].insert(lift(pullRequestEvent)))
      case Some(_) => logger.info(s"Event id [${pullRequestEvent.eventId}] is already exists. skip ForkEvents event.")
    }
  }

  /**
   * Find PullRequestEvent by event id
   *
   * @param eventId event id
   * @return
   */
  def findById(eventId: Long): Option[PullRequestEvents] = {
    run(query[PullRequestEvents].filter(p => p.eventId == lift(eventId))).headOption
  }

}
