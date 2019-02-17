package net.yoshinorin.selfouettie.services

import net.yoshinorin.selfouettie.utils.Logger
import net.yoshinorin.selfouettie.models.EventStatistics
import net.yoshinorin.selfouettie.models.db.{Events, EventsRepository, Users, UsersRepository}
import net.yoshinorin.selfouettie.types.db.{Between, Limit}

trait UsersService extends QuillProvider with Logger {

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
