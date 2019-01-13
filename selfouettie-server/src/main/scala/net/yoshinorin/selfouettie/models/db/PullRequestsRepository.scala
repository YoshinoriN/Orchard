package net.yoshinorin.selfouettie.models.db

import net.yoshinorin.selfouettie.services.QuillProvider
import net.yoshinorin.selfouettie.utils.Logger

trait PullRequestsRepository {

  def insert(issue: PullRequests): Unit
  def find(repoId: Long, pullRequestNo: Int): Option[PullRequests]
  def update = ??? //TODO
  def upsert = ??? //TODO

}

object PullRequestsRepository extends PullRequestsRepository with QuillProvider with Logger {

  import ctx._;

  /**
   * Insert PullRequest
   *
   * @param pullRequest PullRequest case class
   */
  def insert(pullRequest: PullRequests): Unit = {
    this.find(pullRequest.repositoryId, pullRequest.pullRequestNumber) match {
      case None => run(query[PullRequests].insert(lift(pullRequest)))
      case Some(_) => logger.info(s"Repository id [${pullRequest.repositoryId}], Issue id [${pullRequest.pullRequestNumber}] is already exists. skip ForkEvents event.")
    }
  }

  /**
   * Find PullRequest by repository id & issue number
   *
   * @param repoId repository id
   * @param pullRequestNo PullRequest no
   * @return
   */
  def find(repoId: Long, pullRequestNo: Int): Option[PullRequests] = {
    run(query[PullRequests].filter(p => p.repositoryId == lift(repoId) && p.pullRequestNumber == lift(pullRequestNo))).headOption
  }

}
