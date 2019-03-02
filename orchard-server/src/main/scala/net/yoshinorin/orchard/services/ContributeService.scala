package net.yoshinorin.orchard.services

import net.yoshinorin.orchard.models.ContributedRepository
import net.yoshinorin.orchard.models.db._
import net.yoshinorin.orchard.types.EventType
import net.yoshinorin.orchard.utils.Logger

trait ContributeService extends QuillProvider with Logger {

  import ctx._;

  /**
   * Get event statistics by userName
   *
   * @param userName user name
   * @return
   */
  def getContributeCountByRepositoryByUserName(userName: String): List[ContributedRepository] = {

    val q = quote {
      query[Events]
        .join(query[Repositories])
        .on((event, repo) => event.repositoryId == repo.id)
        .filter(_._1.userName == lift(userName))
        .filter(e =>
          liftQuery(Set(
            EventType.IssueCommentEvent.value,
            EventType.IssuesEvent.value,
            EventType.PushEvent.value,
            EventType.PullRequestReviewCommentEvent.value,
            EventType.PullRequestReviewEvent.value,
            EventType.PullRequestEvent.value,
            EventType.ReleaseEvent.value
          )).contains(e._1.eventType))
        .groupBy(r => (r._2.id, r._2.name))
        .map {
          case (repo, name) => {
            (repo, unquote(name.map(_._2.id).size))
          }
        }
    }

    for (x <- run(q)) yield {
      ContributedRepository(
        x._1._1,
        x._1._2,
        x._1._2,
        x._2
      )
    }
  }

}
