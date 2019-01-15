package net.yoshinorin.selfouettie.models.db

import net.yoshinorin.selfouettie.services.QuillProvider
import net.yoshinorin.selfouettie.types.db.{FromTo, Limit}
import net.yoshinorin.selfouettie.utils.Logger

trait EventsRepository {

  def insert(event: Events): Unit
  def findById(id: Long): Option[Events]
  def findByUserName(userName: String, limit: Limit, createdAtBetween: FromTo): Option[Events]

}

object EventsRepository extends EventsRepository with QuillProvider with Logger {

  import ctx._;

  /**
   * Insert Event
   *
   * @param event Events case class
   */
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

  /**
   * Find event by userName
   *
   * @param userName user name
   * @param limit max number of records return from database
   * @param createdAtBetween created at range (from to)
   * @return
   */
  def findByUserName(userName: String, limit: Limit, createdAtBetween: FromTo): Option[Events] = {
    val q = quote {
      query[Events].filter(_.userName == lift(userName)).take(lift(limit.limit))
    }
    if (createdAtBetween.from.isDefined) {
      quote { q.filter(_.createdAt >= lift(createdAtBetween.from.get)) }
    }
    if (createdAtBetween.to.isDefined) {
      quote { q.filter(_.createdAt <= lift(createdAtBetween.to.get)) }
    }
    run(q.sortBy(_.createdAt)(Ord.desc)).headOption
  }

}
