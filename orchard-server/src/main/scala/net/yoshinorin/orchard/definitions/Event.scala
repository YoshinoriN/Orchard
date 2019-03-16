package net.yoshinorin.orchard.definitions.event

sealed abstract class EventType(val value: String)

object EventType {

  object CreateEvent extends EventType("CreateEvent")
  object DeleteEvent extends EventType("DeleteEvent")
  object ForkEvent extends EventType("ForkEvent")
  object IssueCommentEvent extends EventType("IssueCommentEvent")
  object IssuesEvent extends EventType("IssuesEvent")
  object PullRequestEvent extends EventType("PullRequestEvent")
  object PullRequestReviewEvent extends EventType("PullRequestReviewEvent")
  object PullRequestReviewCommentEvent extends EventType("PullRequestReviewCommentEvent")
  object PushEvent extends EventType("PushEvent")
  object ReleaseEvent extends EventType("ReleaseEvent")
  object WatchEvent extends EventType("WatchEvent")
  object Undefined extends EventType("Undefined")

}

object Converter {

  implicit class eventTypeConverter(s: String) {

    def toEventType: EventType = {
      s match {
        case "CreateEvent" => EventType.CreateEvent
        case "DeleteEvent" => EventType.DeleteEvent
        case "ForkEvent" => EventType.ForkEvent
        case "IssueCommentEvent" => EventType.IssueCommentEvent
        case "IssuesEvent" => EventType.IssuesEvent
        case "PullRequestEvent" => EventType.PullRequestEvent
        case "PullRequestReviewEvent" => EventType.PullRequestReviewEvent
        case "PullRequestReviewCommentEvent" => EventType.PullRequestReviewCommentEvent
        case "PushEvent" => EventType.PushEvent
        case "ReleaseEvent" => EventType.ReleaseEvent
        case "WatchEvent" => EventType.WatchEvent
        case _ => EventType.Undefined //TODO: throw Exception is better?
      }
    }

  }

}
