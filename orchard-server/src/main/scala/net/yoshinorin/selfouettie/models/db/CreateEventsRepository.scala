package net.yoshinorin.orchard.models.db

import net.yoshinorin.orchard.services.QuillProvider
import net.yoshinorin.orchard.utils.Logger

trait CreateEventsRepository {

  def insert(createEvent: CreateEvents): Unit
  def findById(id: Long): Option[CreateEvents]

}

object CreateEventsRepository extends CreateEventsRepository with QuillProvider with Logger {

  import ctx._;

  /**
   * Insert CreateEvent
   *
   * @param createEvent CreateEvents case class
   */
  def insert(createEvent: CreateEvents): Unit = {
    this.findById(createEvent.eventId) match {
      case None => run(query[CreateEvents].insert(lift(createEvent)))
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
