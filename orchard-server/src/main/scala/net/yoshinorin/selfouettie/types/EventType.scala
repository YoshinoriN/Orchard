package net.yoshinorin.orchard.types

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
