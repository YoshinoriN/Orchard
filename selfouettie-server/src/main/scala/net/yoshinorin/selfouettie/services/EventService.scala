package net.yoshinorin.selfouettie.services

import net.yoshinorin.selfouettie.models._
import net.yoshinorin.selfouettie.models.db._
import net.yoshinorin.selfouettie.types.{Action, EventType}
import net.yoshinorin.selfouettie.utils.Logger

trait EventService extends QuillProvider with Logger {

  import ctx._;

  private[this] def generateEvent(event: EventObject, action: String): Events = {
    Events(
      event.id,
      event.eventType.value,
      event.userName,
      event.repository.get.id,
      action,
      event.repository.get.name,
      event.createdAt
    )
  }

  def create(event: EventObject): Unit = {

    if (event.id == 0) {
      logger.info("skip insert record. event id is undefined.")
      return
    }

    if (event.userName == "") {
      logger.info("skip insert record. username is undefined.")
      return
    }

    if (event.createdAt == 0) {
      logger.info("skip insert record. created_at is undefined.")
      return
    }

    if (event.event.isEmpty) {
      logger.info("skip insert record. event is undefined.")
      return
    }

    transaction {
      UsersRepository.insert(Users(event.userName, event.createdAt))
      event.repository match {
        case Some(r) => RepositoriesRepository.insert(r)
        case _ => {
          logger.info("skip insert record. repository is undefined.")
          return
        }
      }
      val eventType = event.eventType
      event.event match {
        case None => logger.info("skip insert event detail. event data is nothing.")
        case Some(e) => {
          eventType match {
            case EventType.CreateEvent =>
              EventsRepository.insert(this.generateEvent(event, Action.created.toString))
              CreateEventsRepository.insert(e.asInstanceOf[CreateEvents])
            case EventType.ForkEvent =>
              EventsRepository.insert(this.generateEvent(event, Action.fork.toString))
              ForkEventsRepository.insert(e.asInstanceOf[ForkEvents])
            case EventType.IssueCommentEvent =>
              val issueCommentEvents: IssueCommentEvents = e.asInstanceOf[IssueCommentEvents]
              EventsRepository.insert(this.generateEvent(event, issueCommentEvents.action))
              IssuesRepository.insert(Issues(event.repository.get.id, issueCommentEvents.issueNumber, "TODO"))
              IssueCommentEventsRepository.insert(issueCommentEvents)
            case EventType.IssuesEvent =>
              val issuesEvent: IssueEvents = e.asInstanceOf[IssueEvents]
              IssuesRepository.insert(Issues(event.repository.get.id, issuesEvent.issueNumber, "TODO"))
              EventsRepository.insert(this.generateEvent(event, Action.created.toString))
              IssuesEventsRepository.insert(issuesEvent)
            case EventType.PullRequestEvent =>
              val pullRequestEvents: PullRequestEvents = e.asInstanceOf[PullRequestEvents]
              EventsRepository.insert(this.generateEvent(event, Action.created.toString))
              PullRequestRepository.insert(PullRequests(event.repository.get.id, pullRequestEvents.pullRequestNumber, "TODO", false)) //TODO: update merged Boolean
              PullRequestEventRepository.insert(pullRequestEvents)
            case EventType.PullRequestReviewEvent =>
              val pullRequestReviewEvents: PullRequestReviewEvents = e.asInstanceOf[PullRequestReviewEvents]
              EventsRepository.insert(this.generateEvent(event, Action.created.toString))
              PullRequestRepository.insert(PullRequests(event.repository.get.id, pullRequestReviewEvents.pullRequestNumber, "TODO", false)) //TODO: update merged Boolean

            case EventType.PullRequestReviewCommentEvent =>
              EventsRepository.insert(this.generateEvent(event, Action.created.toString))
            case EventType.PushEvent =>
              EventsRepository.insert(this.generateEvent(event, Action.created.toString))
            case EventType.ReleaseEvent =>
              EventsRepository.insert(this.generateEvent(event, Action.created.toString))
            case EventType.WatchEvent =>
              EventsRepository.insert(this.generateEvent(event, Action.created.toString))
            case EventType.Undefined => logger.info("Undefined event skip insert.")
          }
        }
      }
    }
  }

}
