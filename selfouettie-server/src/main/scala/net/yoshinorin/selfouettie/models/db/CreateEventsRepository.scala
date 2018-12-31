package net.yoshinorin.selfouettie.models.db

import net.yoshinorin.selfouettie.services.QuillProvider
import net.yoshinorin.selfouettie.utils.Logger

trait CreateEventsRepository {

  def insert(cEvent: CreateEvents): Unit
  def findById(id: Long): Option[CreateEvents]

}

object CreateEventsRepository extends CreateEventsRepository with QuillProvider with Logger {

  import ctx._;

  /**
   * Insert CreateEvents
   *
   * @param cEvent CreateEvents case class
   */
  def insert(cEvent: CreateEvents): Unit = {
    this.findById(cEvent.eventId) match {
      case None => run(query[CreateEvents].insert(lift(cEvent)))
      case Some(e) => logger.info(s"Event id [${e.eventId}] is already exists. skip CreateEvents event.")
    }
  }

  /**
   * Find CreateEvent by id
   *
   * @param id event id
   * @return
   */
  def findById(id: Long): Option[CreateEvents] = {
    run(query[CreateEvents].filter(e => e.eventId == lift(id))).headOption
  }

}
