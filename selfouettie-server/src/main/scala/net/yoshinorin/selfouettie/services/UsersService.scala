package net.yoshinorin.selfouettie.services

import net.yoshinorin.selfouettie.models.db.Users
import net.yoshinorin.selfouettie.utils.Logger

trait UsersService extends QuillProvider with Logger {

  import ctx._;

  /**
   * Insert user
   *
   * @param user Users case class
   */
  def insert(user: Users): Unit = {
    this.findByName(user.name) match {
      case None => run(query[Users].insert(lift(user)))
      case Some(u) => logger.info(s"${u.name} is already exists. skip create user.")
    }
  }

  /**
   * Find user by name
   *
   * @param name user name
   * @return
   */
  def findByName(name: String): Option[Users] = {
    run(query[Users].filter(u => u.name == lift(name))).headOption
  }

}
