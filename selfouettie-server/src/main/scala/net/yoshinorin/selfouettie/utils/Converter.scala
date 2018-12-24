package net.yoshinorin.selfouettie.utils

import net.yoshinorin.selfouettie.types.EventType

object Converter {

  implicit class eventTypeConverter(s: String) {

    def toEventType: EventType = {
      s match {
        case "CreateEvent" => EventType.CreateEvent
        case "ForkEvent" => EventType.ForkEvent
        case "IssueCommentEvent" => EventType.IssueCommentEvent
        case "IssuesEvent" => EventType.IssuesEvent
        case "PullRequestEvent" => EventType.PullRequestEvent
        case "PullRequestReviewEvent" => EventType.PullRequestReviewEvent
        case "PullRequestReviewCommentEvent" => EventType.PullRequestReviewCommentEvent
        case "PushEvent" => EventType.PushEvent
        case "ReleaseEvent" => EventType.ReleaseEvent
        case "WatchEvent" => EventType.WatchEvent
        case _ => EventType.CreateEvent //TODO: throw Exception
      }
    }

  }

}
