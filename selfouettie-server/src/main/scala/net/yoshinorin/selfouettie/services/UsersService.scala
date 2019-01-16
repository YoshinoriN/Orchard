package net.yoshinorin.selfouettie.services

import net.yoshinorin.selfouettie.utils.Logger
import net.yoshinorin.selfouettie.models.db.{Events, EventsRepository, Users, UsersRepository}
import net.yoshinorin.selfouettie.types.db.{FromTo, Limit}

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
   * @param createdAtBetween created at range (from to)
   * @return
   */
  def getEventsByUser(userName: String, limit: Limit, createdAtBetween: Option[FromTo] = None): List[Events] = {
    EventsRepository.findByUserName(userName, limit, createdAtBetween)
  }

}
