package net.yoshinorin.orchard.actor

import scala.concurrent.ExecutionContext
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

trait ActorService {

  implicit val actorSystem: ActorSystem = ActorSystem("orchard")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = actorSystem.dispatcher

}
