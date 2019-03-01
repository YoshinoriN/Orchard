package net.yoshinorin.orchard.models.db

import net.yoshinorin.orchard.services.QuillProvider
import net.yoshinorin.orchard.utils.Logger

trait PullRequestReviewEventsRepository {

  def insert(pullRequestReviewEvents: PullRequestReviewEvents): Unit
  def findById(eventId: Long): Option[PullRequestReviewEvents]

}

object PullRequestReviewEventsRepository extends PullRequestReviewEventsRepository with QuillProvider with Logger {

  import ctx._;

  /**
   * Insert PullRequestReviewEvent
   *
   * @param pullRequestReviewEvents PullRequestReviewEvent case class
   */
  def insert(pullRequestReviewEvents: PullRequestReviewEvents): Unit = {
    this.findById(pullRequestReviewEvents.eventId) match {
      case None => run(query[PullRequestReviewEvents].insert(lift(pullRequestReviewEvents)))
      case Some(_) => logger.info(s"Event id [${pullRequestReviewEvents.eventId}] is already exists. skip ForkEvents event.")
    }
  }

  /**
   * Find PullRequestReviewEvent by event id
   *
   * @param eventId event id
   * @return
   */
  def findById(eventId: Long): Option[PullRequestReviewEvents] = {
    run(query[PullRequestReviewEvents].filter(p => p.eventId == lift(eventId))).headOption
  }

}
