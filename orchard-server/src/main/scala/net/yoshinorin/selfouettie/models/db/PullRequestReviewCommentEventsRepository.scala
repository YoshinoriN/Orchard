package net.yoshinorin.orchard.models.db

import net.yoshinorin.orchard.services.QuillProvider
import net.yoshinorin.orchard.utils.Logger

trait PullRequestReviewCommentEventsRepository {

  def insert(pullRequestReviewCommentEvents: PullRequestReviewCommentEvents): Unit
  def findById(eventId: Long): Option[PullRequestReviewCommentEvents]

}

object PullRequestReviewCommentEventsRepository extends PullRequestReviewCommentEventsRepository with QuillProvider with Logger {

  import ctx._;

  /**
   * Insert PullRequestReviewCommentEvent
   *
   * @param pullRequestReviewCommentEvents PullRequestReviewCommentEvent case class
   */
  def insert(pullRequestReviewCommentEvents: PullRequestReviewCommentEvents): Unit = {
    this.findById(pullRequestReviewCommentEvents.eventId) match {
      case None => run(query[PullRequestReviewCommentEvents].insert(lift(pullRequestReviewCommentEvents)))
      case Some(_) => logger.info(s"Event id [${pullRequestReviewCommentEvents.eventId}] is already exists. skip ForkEvents event.")
    }
  }

  /**
   * Find PullRequestReviewCommentEvent by event id
   *
   * @param eventId event id
   * @return
   */
  def findById(eventId: Long): Option[PullRequestReviewCommentEvents] = {
    run(query[PullRequestReviewCommentEvents].filter(p => p.eventId == lift(eventId))).headOption
  }

}
