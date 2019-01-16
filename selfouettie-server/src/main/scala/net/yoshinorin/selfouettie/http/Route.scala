package net.yoshinorin.selfouettie.http

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import io.circe.Encoder
import io.circe.syntax._
import io.circe.generic.semiauto._
import net.yoshinorin.selfouettie.models.db.Events
import net.yoshinorin.selfouettie.services.{EventService, UsersService}
import net.yoshinorin.selfouettie.types.db.Limit
import net.yoshinorin.selfouettie.utils.File

trait Route extends EventService with UsersService {

  //TODO: consider move to service object
  implicit val decodeEvent: Encoder[Events] = deriveEncoder[Events]
  implicit val decodeEvents: Encoder[List[Events]] = Encoder.encodeList[Events]

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
      path(".+".r) { userName =>
        pathEndOrSingleSlash {
          val events = getEventsByUser(userName, Limit(), None).asJson
          complete(HttpEntity(ContentTypes.`application/json`, s"$events"))
        }
      }
    }

  }
}
