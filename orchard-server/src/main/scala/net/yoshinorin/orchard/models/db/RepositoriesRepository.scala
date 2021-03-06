package net.yoshinorin.orchard.models.db

import net.yoshinorin.orchard.services.QuillProvider
import net.yoshinorin.orchard.utils.Logger

trait RepositoriesRepository {

  def insert(repo: Repositories): Unit
  def findById(id: Long): Option[Repositories]

}

object RepositoriesRepository extends RepositoriesRepository with QuillProvider with Logger {

  import ctx._;

  /**
   * Insert repository
   *
   * @param repo Repositories case class
   */
  def insert(repo: Repositories): Unit = {
    this.findById(repo.id) match {
      case None => run(query[Repositories].insert(lift(repo)))
      case Some(r) => logger.info(s"Repository id [${r.id}] is already exists. skip create repository.")
    }
  }

  /**
   * Find repository by id
   *
   * @param id repository id
   * @return
   */
  def findById(id: Long): Option[Repositories] = {
    run(query[Repositories].filter(r => r.id == lift(id))).headOption
  }

}
