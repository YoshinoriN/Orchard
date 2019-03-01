package net.yoshinorin.orchard.models.db

import net.yoshinorin.orchard.services.QuillProvider
import net.yoshinorin.orchard.utils.Logger

trait ForkEventsRepository {

  def insert(forkEvent: ForkEvents): Unit
  def findById(id: Long): Option[ForkEvents]

}

object ForkEventsRepository extends ForkEventsRepository with QuillProvider with Logger {

  import ctx._;

  /**
   * Insert ForkEvents
   *
   * @param forkEvent ForkEvents case class
   */
  def insert(forkEvent: ForkEvents): Unit = {
    this.findById(forkEvent.eventId) match {
      case None => run(query[ForkEvents].insert(lift(forkEvent)))
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
