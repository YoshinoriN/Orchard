package net.yoshinorin.orchard.actor

import akka.actor.Actor
import net.yoshinorin.orchard.services.github.event.EventApiService

class GitHubEventApiActor extends Actor {

  import GitHubEventApiActor._

  override def receive: Receive = {
    case GetEventsJson => EventApiService.save()
  }

}

object GitHubEventApiActor {

  sealed case class GetEventsJson()

}
