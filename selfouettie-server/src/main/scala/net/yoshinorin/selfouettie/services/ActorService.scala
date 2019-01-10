package net.yoshinorin.selfouettie.services

import scala.concurrent.ExecutionContext
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

trait ActorService {

  implicit val actorSystem: ActorSystem = ActorSystem("selfouettie")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = actorSystem.dispatcher

}
