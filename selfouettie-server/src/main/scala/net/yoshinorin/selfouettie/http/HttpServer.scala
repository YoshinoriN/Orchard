package net.yoshinorin.selfouettie.http

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import akka.http.scaladsl.Http
import net.yoshinorin.selfouettie.config.HttpServerConfig
import net.yoshinorin.selfouettie.services.ActorService

object HttpServer extends App with ActorService with Route {

  def start: Unit = {

    val bindingFuture = Http().bindAndHandle(route, HttpServerConfig.host, HttpServerConfig.port)
    Await.ready(bindingFuture, Duration.Inf)
  }

}
