package net.yoshinorin.selfouettie.http

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import io.circe.Encoder
import io.circe.syntax._
import io.circe.generic.semiauto._
import net.yoshinorin.selfouettie.models.{ContributedRepository, EventStatistics}
import net.yoshinorin.selfouettie.models.db.Events
import net.yoshinorin.selfouettie.services.{ContributeService, EventService, UsersService}
import net.yoshinorin.selfouettie.types.db.{Between, Limit}
import net.yoshinorin.selfouettie.utils.File

trait Route extends EventService with UsersService with ContributeService {

  //TODO: consider move to service object
  implicit val encodeEvent: Encoder[Events] = deriveEncoder[Events]
  implicit val encodeEvents: Encoder[List[Events]] = Encoder.encodeList[Events]
  implicit val encodeEventStatistics: Encoder[EventStatistics] = deriveEncoder[EventStatistics]
  implicit val encodeContributeCount: Encoder[ContributedRepository] = deriveEncoder[ContributedRepository]
  implicit val encodeContributeCounts: Encoder[List[ContributedRepository]] = Encoder.encodeList[ContributedRepository]

  //TODO: devide route file
  val route = get {
    pathEndOrSingleSlash {
      complete(HttpEntity(ContentTypes.`application/json`, "{\"message\": \"API is ready\"}"))
    } ~ path("robots.txt") {
      File.get(System.getProperty("user.dir") + "/src/main/resources/robots.txt") match {
        case Some(x) => getFromFile(x.getAbsolutePath)
        case _ => complete(HttpResponse(404, entity = "Not Found."))
      }
    } ~ pathPrefix("users") {
      // hostname/users/YoshinoriN
      pathPrefix(".+".r) { userName =>
        // hostname/users/YoshinoriN
        // hostname/users/YoshinoriN/
        pathEndOrSingleSlash {
          complete(HttpResponse(200, entity = "TODO: show statistics"))
        } ~
          // hostname/users/YoshinoriN/activity
          path("activity") {
            // hostname/users/YoshinoriN/activity?from=<number>
            parameters('from.as[Long]) { from =>
              val events = getEventsByUser(userName, Limit(), Some(Between(Some(from), None))).asJson
              complete(HttpEntity(ContentTypes.`application/json`, s"$events"))
            }
          } ~ // hostname/users/YoshinoriN/statistics
          path("statistics") {
            val eventStatisticsJson = getEventStatisticsByUserName(userName).asJson
            complete(HttpEntity(ContentTypes.`application/json`, s"$eventStatisticsJson"))
          } ~
          path("contributions") {
            val contributeCountJson = getContributeCountByRepositoryByUserName(userName).asJson
            complete(HttpEntity(ContentTypes.`application/json`, s"$contributeCountJson"))
          }
      }
    }

  }
}
