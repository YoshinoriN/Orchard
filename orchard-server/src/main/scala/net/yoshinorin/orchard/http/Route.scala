package net.yoshinorin.orchard.http

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import io.circe.syntax._
import net.yoshinorin.orchard.definitions.db.manipulation._
import net.yoshinorin.orchard.services.{ContributeService, UsersService}
import net.yoshinorin.orchard.utils.File

trait Route extends UsersService with ContributeService {

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
          } ~
          path("first-time-event") {
            val event = getFirstTimeEventByUserName(userName).asJson
            complete(HttpEntity(ContentTypes.`application/json`, s"$event"))
          }
      }
    }

  }
}
