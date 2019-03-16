package net.yoshinorin.orchard.actor

import akka.actor.Actor
import net.yoshinorin.orchard.services.github.event.json.GitHubEventJsonService

class GitHubEventJsonActor extends Actor {

  import GitHubEventJsonActor._

  override def receive: Receive = {
    case GetEventsJson => GitHubEventJsonService.save()
  }

}

object GitHubEventJsonActor {

  sealed case class GetEventsJson()

}
