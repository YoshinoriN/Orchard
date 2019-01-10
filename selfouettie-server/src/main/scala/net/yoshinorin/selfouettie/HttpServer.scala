package net.yoshinorin.selfouettie

import scala.io.StdIn
import akka.http.scaladsl.Http
import net.yoshinorin.selfouettie.services.ActorService

object HttpServer extends App with ActorService with Route {

  val bindingFuture = Http().bindAndHandle(route, config.HttpServer.host, config.HttpServer.port)

  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => actorSystem.terminate())

}
