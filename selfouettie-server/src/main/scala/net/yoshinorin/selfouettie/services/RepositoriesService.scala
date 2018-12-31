package net.yoshinorin.selfouettie.services

import net.yoshinorin.selfouettie.models.Repositories
import net.yoshinorin.selfouettie.utils.Logger

trait RepositoriesService extends QuillProvider with Logger {

  import ctx._;

  /**
   * Insert repository
   *
   * @param user Repositories case class
   */
  def insert(repo: Repositories): Unit = {
    this.findById(repo.id) match {
      case None => run(query[Repositories].insert(lift(repo)))
      case Some(r) => logger.info(s"Repository id [${r.id}] is already exists. skip create user.")
    }
  }

  /**
   * Find repository by id
   *
   * @param repo repository id
   * @return
   */
  def findById(repo: Long): Option[Repositories] = {
    run(query[Repositories].filter(r => r.id == lift(repo))).headOption
  }

}
