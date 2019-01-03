package net.yoshinorin.selfouettie.models.db

import net.yoshinorin.selfouettie.services.QuillProvider
import net.yoshinorin.selfouettie.utils.Logger

trait WatchEventsRepository {

  def insert(watchEvents: WatchEvents): Unit
  def findById(eventId: Long): Option[WatchEvents]

}

object WatchEventsRepository extends WatchEventsRepository with QuillProvider with Logger {

  import ctx._;

  /**
   * Insert WatchEvents
   *
   * @param watchEvents WatchEvents case class
   */
  def insert(watchEvents: WatchEvents): Unit = {
    this.findById(watchEvents.eventId) match {
      case None => run(query[WatchEvents].insert(lift(watchEvents)))
      case Some(_) => logger.info(s"Event id [${watchEvents.eventId}] is already exists. skip ForkEvents event.")
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
