package net.yoshinorin.selfouettie.models.db

import net.yoshinorin.selfouettie.services.QuillProvider
import net.yoshinorin.selfouettie.utils.Logger

trait DeleteEventsRepository {

  def insert(deleteEvent: DeleteEvents): Unit
  def findById(id: Long): Option[DeleteEvents]

}

object DeleteEventsRepository extends DeleteEventsRepository with QuillProvider with Logger {

  import ctx._;

  /**
   * Insert DeleteEvents
   *
   * @param deleteEvent DeleteEvents case class
   */
  def insert(deleteEvent: DeleteEvents): Unit = {
    this.findById(deleteEvent.eventId) match {
      case None => run(query[DeleteEvents].insert(lift(deleteEvent)))
      case Some(e) => logger.info(s"Event id [${e.eventId}] is already exists. skip CreateEvents event.")
    }
  }

  /**
   * Find DeleteEvents by id
   *
   * @param id event id
   * @return
   */
  def findById(id: Long): Option[DeleteEvents] = {
    run(query[DeleteEvents].filter(e => e.eventId == lift(id))).headOption
  }

}
