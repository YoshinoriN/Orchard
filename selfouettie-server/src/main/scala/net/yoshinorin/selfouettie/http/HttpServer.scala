package net.yoshinorin.selfouettie.http

import scala.io.StdIn
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import akka.http.scaladsl.Http
import net.yoshinorin.selfouettie.config.HttpServerConfig
import net.yoshinorin.selfouettie.services.ActorService

object HttpServer extends App with ActorService with Route {

  def start: Unit = {

    val bindingFuture = Http().bindAndHandle(route, HttpServerConfig.host, HttpServerConfig.port)

    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => actorSystem.terminate())

    Await.ready(bindingFuture, Duration.Inf)
  }

}
