package net.yoshinorin.selfouettie.models.db

import net.yoshinorin.selfouettie.services.QuillProvider
import net.yoshinorin.selfouettie.types.db.{Between, Limit}
import net.yoshinorin.selfouettie.utils.Logger

trait EventsRepository {

  def insert(event: Events): Unit
  def findById(id: Long): Option[Events]
  def findByUserName(userName: String, limit: Limit, createdAtBetween: Option[Between] = None): List[Events]

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
   * @param betweenCreatedAt created at range (from to)
   * @return
   */
  def findByUserName(userName: String, limit: Limit, betweenCreatedAt: Option[Between] = None): List[Events] = {
    val q = betweenCreatedAt match {
      case Some(ft) => {
        if (ft.from.isDefined && !ft.to.isDefined) {
          quote { query[Events].filter(_.createdAt >= lift(ft.from.get)) }
        } else if (ft.to.isDefined && !ft.from.isDefined) {
          quote { query[Events].filter(_.createdAt <= lift(ft.to.get)) }
        } else {
          quote { query[Events].filter(_.createdAt >= lift(ft.from.get)).filter(_.createdAt <= lift(ft.to.get)) }
        }
      }
      case _ => quote { query[Events].filter(_.createdAt != 0) }
    }
    run(q.filter(_.userName == lift(userName)).take(lift(limit.limit)).sortBy(_.createdAt)(Ord.desc))
  }

  /**
   * Get event type by userName
   *
   * @param userName user name
   * @return
   */
  def getEventsByUserName(userName: String): List[String] = {
    run(query[Events].filter(_.userName == lift(userName)).map(_.eventType))
  }

}
