package net.yoshinorin.orchard.actor

import akka.actor.Actor

class GitHubEventActor extends Actor {

  import GitHubEventActor._

  override def receive: Receive = {
    case GetEvents => println("TODO")
  }

}

object GitHubEventActor {

  sealed case class GetEvents()

}
