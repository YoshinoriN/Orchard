package net.yoshinorin.orchard.models.db

import net.yoshinorin.orchard.services.QuillProvider
import net.yoshinorin.orchard.utils.Logger

trait IssuesRepository {

  def insert(issue: Issues): Unit
  def find(repoId: Long, issueNo: Int): Option[Issues]

}

object IssuesRepository extends IssuesRepository with QuillProvider with Logger {

  import ctx._;

  /**
   * Insert Issues
   *
   * @param issue Issues case class
   */
  def insert(issue: Issues): Unit = {
    this.find(issue.repositoryId, issue.issueNumber) match {
      case None => run(query[Issues].insert(lift(issue)))
      case Some(i) => logger.info(s"Repository id [${issue.repositoryId}], Issue id [${issue.issueNumber}] is already exists. skip ForkEvents event.")
    }
  }

  /**
   * Find issue by repository id & issue number
   *
   * @param repoId repository id
   * @param issueNo issue no
   * @return
   */
  def find(repoId: Long, issueNo: Int): Option[Issues] = {
    run(query[Issues].filter(i => i.repositoryId == lift(repoId) && i.issueNumber == lift(issueNo))).headOption
  }

}
