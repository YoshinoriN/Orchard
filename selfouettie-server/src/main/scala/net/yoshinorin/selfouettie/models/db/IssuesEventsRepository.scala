package net.yoshinorin.selfouettie.models.db

import net.yoshinorin.selfouettie.services.QuillProvider
import net.yoshinorin.selfouettie.utils.Logger

trait IssuesEventsRepository {

  def insert(issuesEvent: IssuesEvents): Unit
  def findById(id: Long): Option[IssuesEvents]

}

object IssuesEventsRepository extends IssuesEventsRepository with QuillProvider with Logger {

  import ctx._;

  /**
   * Insert IssuesEvents
   *
   * @param issuesEvent IssuesEvents case class
   */
  def insert(issuesEvent: IssuesEvents): Unit = {
    this.findById(issuesEvent.eventId) match {
      case None => run(query[IssuesEvents].insert(lift(issuesEvent)))
      case Some(e) => logger.info(s"Event id [${e.eventId}] is already exists. skip ForkEvents event.")
    }
  }

  /**
   * Find IssuesEvents by id
   *
   * @param id event id
   * @return
   */
  def findById(id: Long): Option[IssuesEvents] = {
    run(query[IssuesEvents].filter(e => e.eventId == lift(id))).headOption
  }

}
