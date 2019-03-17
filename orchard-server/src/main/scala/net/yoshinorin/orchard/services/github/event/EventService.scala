package net.yoshinorin.orchard.services.github.event

import io.circe.Json
import net.yoshinorin.orchard.definitions.event.EventType
import net.yoshinorin.orchard.definitions.event.Converter.eventTypeConverter
import net.yoshinorin.orchard.models.db._
import net.yoshinorin.orchard.services.QuillProvider
import net.yoshinorin.orchard.services.github.event.json._
import net.yoshinorin.orchard.utils.Logger

object EventService extends QuillProvider with Logger {

  import ctx._;

  /**
   * Create each xEvent Instance from JSON and Events instance
   *
   * @param event Events Instance
   * @param json JSON
   * @return
   */
  def createEventInstance(event: Events, json: Json): Option[EventBase] = {

    event.eventType.toEventType match {
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

  /**
   * Insert Issue by repository & JSON
   *
   * @param repository repository
   * @param json JSON
   */
  private[this] def insertIssue(repository: Repositories, json: Json): Unit = {
    Issue(repository, json).getConvertedCaseClass.map { _.insert }
  }

  /**
   * Insert PullRequest by repository & JSON
   *
   * @param repository repository
   * @param json JSON
   */
  private[this] def insertPullRequest(repository: Repositories, json: Json): Unit = {
    PullRequest(repository, json).getConvertedCaseClass.map { _.insert }
  }

  /**
   * Insert issue or pullrequest befor insert each events
   *
   * @param eventType event type
   * @param repository repository
   * @param json JSON
   */
  private[this] def preInsert(event: Events, repository: Repositories, json: Json): Unit = {

    //FIXME
    UsersRepository.insert(Users(event.userName, event.createdAt))

    event.eventType.toEventType match {
      case EventType.IssueCommentEvent => this.insertIssue(repository, json)
      case EventType.IssuesEvent => this.insertIssue(repository, json)
      case EventType.PullRequestEvent => this.insertPullRequest(repository, json)
      case EventType.PullRequestReviewEvent => this.insertPullRequest(repository, json)
      case EventType.PullRequestReviewCommentEvent => this.insertPullRequest(repository, json)
      case _ => None
    }

  }

  /**
   * Insert repository & issue & pull-request & each events from JSON
   *
   * @param json JSON
   */
  def insert(json: Json): Unit = {

    transaction {

      //val rawQuery = quote { """SET NAMES utf8mb4""" }
      //run(rawQuery)

      //FIXME
      Repository(json).getConvertedCaseClass.map { _.insert }

      for {
        repo <- Repository(json).getConvertedCaseClass
        event <- Event(repo, json).getConvertedCaseClass
        newEvent <- event.insertAndGetInstance
        eventInstance <- {
          preInsert(newEvent, repo, json)
          this.createEventInstance(newEvent, json)
        }
        result = eventInstance.insert
      } yield {}
    }
  }
}
