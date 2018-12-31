package net.yoshinorin.selfouettie.models.db

import net.yoshinorin.selfouettie.services.QuillProvider
import net.yoshinorin.selfouettie.utils.Logger

trait IssueCommentEventsRepository {

  def insert(ecEvent: IssueCommentEvents): Unit
  def findById(id: Long): Option[IssueCommentEvents]

}

object IssueCommentEventsRepository extends IssueCommentEventsRepository with QuillProvider with Logger {

  import ctx._;

  /**
   * Insert IssueCommentEvents
   *
   * @param ecEvent IssueCommentEvents case class
   */
  def insert(ecEvent: IssueCommentEvents): Unit = {
    this.findById(ecEvent.eventId) match {
      case None => run(query[IssueCommentEvents].insert(lift(ecEvent)))
      case Some(e) => logger.info(s"Event id [${e.eventId}] is already exists. skip ForkEvents event.")
    }
  }

  /**
   * Find IssueCommentEvents by id
   *
   * @param id event id
   * @return
   */
  def findById(id: Long): Option[IssueCommentEvents] = {
    run(query[IssueCommentEvents].filter(e => e.eventId == lift(id))).headOption
  }

}
