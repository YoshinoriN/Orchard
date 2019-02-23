package net.yoshinorin.selfouettie.http

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.io.StdIn
import akka.http.scaladsl.Http
import net.yoshinorin.selfouettie.config.HttpServerConfig
import net.yoshinorin.selfouettie.services.ActorService

object HttpServer extends App with ActorService with Route {

  def run(args: List[String]): Unit = {

    if (args.contains("dev") || args.contains("-d")) {
      val bindingFuture = Http().bindAndHandle(route, HttpServerConfig.host, HttpServerConfig.port)
      StdIn.readLine()
      bindingFuture
        .flatMap(_.unbind())
        .onComplete(_ => actorSystem.terminate())

    } else {
      val bindingFuture = Http().bindAndHandle(route, HttpServerConfig.host, HttpServerConfig.port)
      Await.ready(bindingFuture, Duration.Inf)
    }
  }

}
