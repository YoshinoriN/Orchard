package net.yoshinorin.orchard.services

import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder
import net.yoshinorin.orchard.definitions.db.manipulation._
import net.yoshinorin.orchard.utils.Logger
import net.yoshinorin.orchard.models.EventStatistics
import net.yoshinorin.orchard.models.db.{Events, EventsRepository, Users, UsersRepository}

trait UsersService extends QuillProvider with Logger {

  implicit val encodeEvent: Encoder[Events] = deriveEncoder[Events]
  implicit val encodeEvents: Encoder[List[Events]] = Encoder.encodeList[Events]
  implicit val encodeEventStatistics: Encoder[EventStatistics] = deriveEncoder[EventStatistics]

  /**
   * Get user
   *
   * @param userName user name
   * @return
   */
  def getUser(userName: String): Option[Users] = {
    UsersRepository.findByName(userName)
  }

  /**
   * Get user events list
   *
   * @param userName user name
   * @param limit max number of records return from database
   * @param betweenCreatedAt created at range (from to)
   * @return
   */
  def getEventsByUser(userName: String, limit: Limit, betweenCreatedAt: Option[Between] = None): List[Events] = {
    EventsRepository.findByUserName(userName, limit, betweenCreatedAt)
  }

  /**
   * Get first time event of user
   *
   * @param userName user name
   * @return
   */
  def getFirstTimeEventByUserName(userName: String): Option[Events] = {
    EventsRepository.getFirstTimeEventByUserName(userName)
  }

  /**
   * Get event statistics by userName
   *
   * @param userName user name
   * @return
   */
  def getEventStatisticsByUserName(userName: String): EventStatistics = {
    val e = EventsRepository.getEventsByUserName(userName).groupBy(identity(_)).mapValues(_.size)

    EventStatistics(
      e getOrElse ("CreateEvent", 0),
      e getOrElse ("DeleteEvent", 0),
      e getOrElse ("ForkEvent", 0),
      e getOrElse ("IssueCommentEvent", 0),
      e getOrElse ("IssuesEvent", 0),
      e getOrElse ("PullRequestEvent", 0),
      e getOrElse ("PullRequestReviewCommentEvent", 0),
      e getOrElse ("PullRequestReviewEvent", 0),
      e getOrElse ("PushEvent", 0),
      e getOrElse ("ReleaseEvent", 0),
      e getOrElse ("WatchEvent", 0)
    )
  }

}
