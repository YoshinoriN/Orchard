package net.yoshinorin.selfouettie.http

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import net.yoshinorin.selfouettie.utils.File

trait Route {

  val route = get {
    pathEndOrSingleSlash {
      complete(HttpEntity(ContentTypes.`application/json`, "{\"message\": \"API is ready\"}"))
    } ~ path("robots.txt") {
      File.get(System.getProperty("user.dir") + "/src/main/resources/robots.txt") match {
        case Some(x) => getFromFile(x.getAbsolutePath)
        case _ => complete(HttpResponse(404, entity = "Not Found."))
      }
    }
  }
}
