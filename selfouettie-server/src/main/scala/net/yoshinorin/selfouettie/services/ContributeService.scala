package net.yoshinorin.selfouettie.services

import net.yoshinorin.selfouettie.models.ContributeCount
import net.yoshinorin.selfouettie.models.db._
import net.yoshinorin.selfouettie.utils.Logger

trait ContributeService extends QuillProvider with Logger {

  import ctx._;

  /**
   * Get event statistics by userName
   *
   * @param userName user name
   * @return
   */
  def getContributeCountByRepositoryByUserName(userName: String): List[ContributeCount] = {

    /* Why not work???

    val rawQuery = quote { (name: String) =>
      infix"""
        SELECT repositories.id, repositories.name, count(repositories.id) as cnt
          FROM events
        INNER JOIN repositories ON repositories.id = events.repository_id
         WHERE events.user_name = $name
        GROUP BY repositories.id;
      """.as[Query[(Long, String, Int)]]
    }
    val result = run(rawQuery($userName))
     */

    val q = quote {
      query[Events]
        .join(query[Repositories])
        .on(_.repositoryId == _.id)
        .filter(_._1.userName == lift(userName))
        .groupBy(r => (r._2.id, r._2.name))
        .map {
          case (repo, name) => {
            (repo, unquote(name.map(_._2.id).size))
          }
        }
    }

    for (x <- run(q)) yield {
      ContributeCount(
        x._1._1,
        x._1._2,
        x._2
      )
    }
  }

}
