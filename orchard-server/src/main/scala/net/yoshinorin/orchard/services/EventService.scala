package net.yoshinorin.orchard.services

import net.yoshinorin.orchard.models._
import net.yoshinorin.orchard.models.db._
import net.yoshinorin.orchard.definitions.event.EventType
import net.yoshinorin.orchard.definitions.action.ActionType
import net.yoshinorin.orchard.utils.Logger

object EventService extends QuillProvider with Logger {

  import ctx._;

  private def generateEvent(event: EventObject, action: String): Events = {
    Events(event.id, event.eventType.value, event.userName, event.repository.id, action, event.repository.name, event.createdAt)
  }

  def create(event: EventObject): Unit = {

    transaction {

      //val rawQuery = quote { """SET NAMES utf8mb4""" }
      //run(rawQuery)

      UsersRepository.insert(Users(event.userName, event.createdAt))
      RepositoriesRepository.insert(event.repository)
      if (event.issue.isDefined) {
        IssuesRepository.insert(event.issue.get)
      }
      if (event.pullRequest.isDefined) {
        PullRequestsRepository.insert(event.pullRequest.get) //TODO: update merged Boolean
      }

      val eventType = event.eventType
      event.event match {
        case None => logger.info("skip insert event detail. event data is nothing.")
        case Some(e) => {
          eventType match {
            case EventType.CreateEvent =>
              EventsRepository.insert(this.generateEvent(event, ActionType.Created.value))
              CreateEventsRepository.insert(e.asInstanceOf[CreateEvents])
            case EventType.DeleteEvent =>
              EventsRepository.insert(this.generateEvent(event, ActionType.Created.value))
              DeleteEventsRepository.insert(e.asInstanceOf[DeleteEvents])
            case EventType.ForkEvent =>
              EventsRepository.insert(this.generateEvent(event, ActionType.Fork.value))
              ForkEventsRepository.insert(e.asInstanceOf[ForkEvents])
            case EventType.IssueCommentEvent =>
              val issueCommentEvents: IssueCommentEvents = e.asInstanceOf[IssueCommentEvents]
              EventsRepository.insert(this.generateEvent(event, issueCommentEvents.action))
              IssueCommentEventsRepository.insert(issueCommentEvents)
            case EventType.IssuesEvent =>
              val issuesEvent: IssueEvents = e.asInstanceOf[IssueEvents]
              EventsRepository.insert(this.generateEvent(event, ActionType.Created.value))
              IssuesEventsRepository.insert(issuesEvent)
            case EventType.PullRequestEvent =>
              val pullRequestEvents: PullRequestEvents = e.asInstanceOf[PullRequestEvents]
              EventsRepository.insert(this.generateEvent(event, ActionType.Created.value))
              PullRequestEventsRepository.insert(pullRequestEvents)
            case EventType.PullRequestReviewEvent =>
              val pullRequestReviewEvents: PullRequestReviewEvents = e.asInstanceOf[PullRequestReviewEvents]
              EventsRepository.insert(this.generateEvent(event, ActionType.Created.value))
              PullRequestReviewEventsRepository.insert(pullRequestReviewEvents)
            case EventType.PullRequestReviewCommentEvent =>
              val pullRequestReviewCommentEvents: PullRequestReviewCommentEvents = e.asInstanceOf[PullRequestReviewCommentEvents]
              EventsRepository.insert(this.generateEvent(event, ActionType.Created.value))
              PullRequestReviewCommentEventsRepository.insert(pullRequestReviewCommentEvents)
            case EventType.PushEvent =>
              val pushEvents: PushEvents = e.asInstanceOf[PushEvents]
              EventsRepository.insert(this.generateEvent(event, ActionType.Created.value))
              PushEventsRepository.insert(pushEvents)
            case EventType.ReleaseEvent =>
              val releaseEvents: ReleaseEvents = e.asInstanceOf[ReleaseEvents]
              EventsRepository.insert(this.generateEvent(event, ActionType.Created.value))
              ReleaseEventsRepository.insert(releaseEvents)
            case EventType.WatchEvent =>
              val watchEvents: WatchEvents = e.asInstanceOf[WatchEvents]
              EventsRepository.insert(this.generateEvent(event, ActionType.Created.value))
              WatchEventsRepository.insert(watchEvents)
            case EventType.Undefined => logger.info("Undefined event skip insert.")
          }
        }
      }
    }
  }
}
