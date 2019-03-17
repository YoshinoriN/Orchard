package net.yoshinorin.orchard.services.github.event.json

import io.circe.Json
import net.yoshinorin.orchard.definitions.event.EventType
import net.yoshinorin.orchard.models.db.Events

object EventFactory {

  def createInstance(eventType: EventType, event: Events, json: Json): Option[EventBase] = {

    eventType match {
      case EventType.CreateEvent => Option(CreateEvent(event, json))
      case EventType.DeleteEvent => Option(DeleteEvent(event, json))
      case EventType.ForkEvent => Option(ForkEvent(event))
      case EventType.IssueCommentEvent => Option(IssueCommentEvent(event, json))
      case EventType.IssuesEvent => Option(IssuesEvent(event, json))
      case EventType.PullRequestEvent => Option(PullRequestEvent(event, json))
      case EventType.PullRequestReviewEvent => Option(PullRequestReviewEvent(event, json))
      case EventType.PullRequestReviewCommentEvent => Option(PullRequestReviewCommentEvent(event, json))
      case EventType.PushEvent => Option(PushEvent(event, json))
      case EventType.ReleaseEvent => Option(ReleaseEvent(event, json))
      case EventType.WatchEvent => Option(WatchEvent(event, json))
      case _ => None
    }

  }

}
