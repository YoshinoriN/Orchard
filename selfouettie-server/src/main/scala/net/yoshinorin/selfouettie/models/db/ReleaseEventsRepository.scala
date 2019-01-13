package net.yoshinorin.selfouettie.models.db

import net.yoshinorin.selfouettie.services.QuillProvider
import net.yoshinorin.selfouettie.utils.Logger

trait ReleaseEventsRepository {

  def insert(releaseEvents: ReleaseEvents): Unit
  def findById(eventId: Long): Option[ReleaseEvents]

}

object ReleaseEventsRepository extends ReleaseEventsRepository with QuillProvider with Logger {

  import ctx._;

  /**
   * Insert ReleaseEvents
   *
   * @param releaseEvents ReleaseEvents case class
   */
  def insert(releaseEvents: ReleaseEvents): Unit = {
    this.findById(releaseEvents.eventId) match {
      case None => run(query[ReleaseEvents].insert(lift(releaseEvents)))
      case Some(_) => logger.info(s"Event id [${releaseEvents.eventId}] is already exists. skip ForkEvents event.")
    }
  }

  /**
   * Find ReleaseEvents by event id
   *
   * @param eventId event id
   * @return
   */
  def findById(eventId: Long): Option[ReleaseEvents] = {
    run(query[ReleaseEvents].filter(r => r.eventId == lift(eventId))).headOption
  }

}
