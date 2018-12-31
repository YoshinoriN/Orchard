package net.yoshinorin.selfouettie.models.db

import net.yoshinorin.selfouettie.services.QuillProvider
import net.yoshinorin.selfouettie.utils.Logger

trait EventsRepository extends QuillProvider with Logger {

  import ctx._;

  def insert(event: Events): Unit = {
    this.findById(event.id) match {
      case None => run(query[Events].insert(lift(event)))
      case Some(e) => logger.info(s"Event id [${e.id}] is already exists. skip create event.")
    }
  }

  /**
   * Find event by id
   *
   * @param id event id
   * @return
   */
  def findById(id: Long): Option[Events] = {
    run(query[Events].filter(e => e.id == lift(id))).headOption
  }

}
