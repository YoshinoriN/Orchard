package net.yoshinorin.selfouettie.models.db

import net.yoshinorin.selfouettie.services.QuillProvider
import net.yoshinorin.selfouettie.utils.Logger

trait ForkEventsRepository {

  def insert(fEvent: ForkEvents): Unit
  def findById(id: Long): Option[ForkEvents]

}

object ForkEventsRepository extends ForkEventsRepository with QuillProvider with Logger {

  import ctx._;

  /**
   * Insert ForkEvents
   *
   * @param fEvent ForkEvents case class
   */
  def insert(fEvent: ForkEvents): Unit = {
    this.findById(fEvent.eventId) match {
      case None => run(query[ForkEvents].insert(lift(fEvent)))
      case Some(e) => logger.info(s"Event id [${e.eventId}] is already exists. skip ForkEvents event.")
    }
  }

  /**
   * Find ForkEvents by id
   *
   * @param id event id
   * @return
   */
  def findById(id: Long): Option[ForkEvents] = {
    run(query[ForkEvents].filter(e => e.eventId == lift(id))).headOption
  }

}
