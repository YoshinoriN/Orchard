package net.yoshinorin.selfouettie.http

import scala.io.StdIn
import akka.http.scaladsl.Http
import net.yoshinorin.selfouettie.config.HttpServerConfig
import net.yoshinorin.selfouettie.services.ActorService

object HttpServer extends App with ActorService with Route {

  val bindingFuture = Http().bindAndHandle(route, HttpServerConfig.host, HttpServerConfig.port)

  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => actorSystem.terminate())

}
