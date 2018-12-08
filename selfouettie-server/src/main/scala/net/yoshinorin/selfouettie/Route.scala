package net.yoshinorin.selfouettie

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._

trait Route {

  val route = get {
    pathEndOrSingleSlash {
      complete(HttpEntity(ContentTypes.`application/json`, "{\"message\": \"API is ready\"}"))
    }
  }
}
