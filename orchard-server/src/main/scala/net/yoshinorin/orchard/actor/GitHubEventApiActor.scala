package net.yoshinorin.orchard.actor

import akka.actor.Actor

class GitHubEventApiActor extends Actor {

  override def receive: Receive = ???

}

object GitHubEventApiActor {

  sealed case class GetEventsJson()

}
