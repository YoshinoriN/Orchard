package net.yoshinorin.selfouettellia

import scala.concurrent.ExecutionContext
import scala.io.StdIn

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

object HttpServer extends App with Route {

  implicit val actorSystem: ActorSystem = ActorSystem("selfouettellia")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = actorSystem.dispatcher

  val bindingFuture = Http().bindAndHandle(route, config.HttpServer.host, config.HttpServer.port)

  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => actorSystem.terminate())

}
