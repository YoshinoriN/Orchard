package net.yoshinorin.orchard.actor

import akka.actor.Actor

class GitHubEventJsonActor extends Actor {

  import GitHubEventJsonActor._

  override def receive: Receive = {
    case GetEventsJson => println("TODO")
  }

}

object GitHubEventJsonActor {

  sealed case class GetEventsJson()

}
