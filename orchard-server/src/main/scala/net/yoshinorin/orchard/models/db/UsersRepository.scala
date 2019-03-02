package net.yoshinorin.orchard.models.db

import net.yoshinorin.orchard.services.QuillProvider
import net.yoshinorin.orchard.utils.Logger

trait UsersRepository {

  def insert(user: Users): Unit
  def findByName(name: String): Option[Users]

}

object UsersRepository extends UsersRepository with QuillProvider with Logger {

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
