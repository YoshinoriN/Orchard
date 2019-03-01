package net.yoshinorin.orchard.models.db

import net.yoshinorin.orchard.services.QuillProvider
import net.yoshinorin.orchard.utils.Logger

trait WatchEventsRepository {

  def insert(watchEvent: WatchEvents): Unit
  def findById(eventId: Long): Option[WatchEvents]

}

object WatchEventsRepository extends WatchEventsRepository with QuillProvider with Logger {

  import ctx._;

  /**
   * Insert WatchEvents
   *
   * @param watchEvent WatchEvents case class
   */
  def insert(watchEvent: WatchEvents): Unit = {
    this.findById(watchEvent.eventId) match {
      case None => run(query[WatchEvents].insert(lift(watchEvent)))
      case Some(_) => logger.info(s"Event id [${watchEvent.eventId}] is already exists. skip ForkEvents event.")
    }
  }

  /**
   * Find WatchEvents by event id
   *
   * @param eventId event id
   * @return
   */
  def findById(eventId: Long): Option[WatchEvents] = {
    run(query[WatchEvents].filter(w => w.eventId == lift(eventId))).headOption
  }

}
