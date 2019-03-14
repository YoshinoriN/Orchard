package net.yoshinorin.orchard.definitions.action

import net.yoshinorin.orchard.definitions.event.EventType
import net.yoshinorin.orchard.utils.Converter.eventTypeConverter

sealed abstract class ActionType(val value: String)

object ActionType {

  object Opened extends ActionType("opened")
  object Created extends ActionType("created")
  object Commented extends ActionType("commented")
  object Edited extends ActionType("edited")
  object Deleted extends ActionType("deleted")
  object Transferred extends ActionType("transferred")
  object Closed extends ActionType("closed")
  object Fork extends ActionType("fork")
  object Reopened extends ActionType("reopened")
  object Assigned extends ActionType("assigned")
  object Unassigned extends ActionType("unassigned")
  object Labeled extends ActionType("labeled")
  object Unlabeled extends ActionType("unlabeled")
  object Milestoned extends ActionType("milestoned")
  object Demilestoned extends ActionType("demilestoned")
  object Started extends ActionType("started")
  object Published extends ActionType("published")
  object Submitted extends ActionType("submitted")
  object Dismissed extends ActionType("dismissed")
  object ReviewRequested extends ActionType("review_requested")
  object ReviewRequestRemoved extends ActionType("review_request_removed")

}

object DefaultAction {

  /**
   * get default action by event
   *
   * @param eventType
   * @return
   */
  def get(eventType: String): ActionType = {
    this.get(eventType.toEventType)
  }

  /**
   * get default action by event
   *
   * @param eventType
   * @return
   */
  def get(eventType: EventType): ActionType = {

    eventType match {
      case EventType.CreateEvent => ActionType.Created
      case EventType.DeleteEvent => ActionType.Created
      case EventType.ForkEvent => ActionType.Fork
      case EventType.IssueCommentEvent => ActionType.Commented
      case EventType.IssuesEvent => ActionType.Created
      case EventType.PullRequestEvent => ActionType.Created
      case EventType.PullRequestReviewEvent => ActionType.Created
      case EventType.PullRequestReviewCommentEvent => ActionType.Created
      case EventType.PushEvent => ActionType.Created
      case EventType.ReleaseEvent => ActionType.Created
      case EventType.WatchEvent => ActionType.Created
    }

  }

}
