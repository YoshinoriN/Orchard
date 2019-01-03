package net.yoshinorin.selfouettie.models.db

import net.yoshinorin.selfouettie.services.QuillProvider
import net.yoshinorin.selfouettie.utils.Logger

trait PushEventsRepository {

  def insert(pushEvents: PushEvents): Unit
  def findById(eventId: Long): Option[PushEvents]

}

object PushEventsRepository extends PushEventsRepository with QuillProvider with Logger {

  import ctx._;

  /**
   * Insert PushEvents
   *
   * @param pushEvents PushEvents case class
   */
  def insert(pushEvents: PushEvents): Unit = {
    this.findById(pushEvents.eventId) match {
      case None => run(query[PushEvents].insert(lift(pushEvents)))
      case Some(_) => logger.info(s"Event id [${pushEvents.eventId}] is already exists. skip ForkEvents event.")
    }
  }

  /**
   * Find PushEvents by event id
   *
   * @param eventId event id
   * @return
   */
  def findById(eventId: Long): Option[PushEvents] = {
    run(query[PushEvents].filter(p => p.eventId == lift(eventId))).headOption
  }

}
