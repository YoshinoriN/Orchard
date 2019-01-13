package net.yoshinorin.selfouettie.services

import java.time.ZonedDateTime

import io.circe.parser.parse
import io.circe.{HCursor, Json}
import net.yoshinorin.selfouettie.models._
import net.yoshinorin.selfouettie.models.db._
import net.yoshinorin.selfouettie.types.{Action, EventType}
import net.yoshinorin.selfouettie.utils.Converter.eventTypeConverter
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

    //FIXME: more clean
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
            case EventType.DeleteEvent =>
              EventsRepository.insert(this.generateEvent(event, Action.created.toString))
              DeleteEventsRepository.insert(e.asInstanceOf[DeleteEvents])
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
              PullRequestReviewEventsRepository.insert(pullRequestReviewEvents)
            case EventType.PullRequestReviewCommentEvent =>
              val pullRequestReviewCommentEvents: PullRequestReviewCommentEvents = e.asInstanceOf[PullRequestReviewCommentEvents]
              EventsRepository.insert(this.generateEvent(event, Action.created.toString))
              PullRequestRepository.insert(PullRequests(event.repository.get.id, pullRequestReviewCommentEvents.pullRequestNumber, "TODO", false)) //TODO: update merged Boolean
              PullRequestReviewCommentEventsRepository.insert(pullRequestReviewCommentEvents)
            case EventType.PushEvent =>
              val pushEvents: PushEvents = e.asInstanceOf[PushEvents]
              EventsRepository.insert(this.generateEvent(event, Action.created.toString))
              PushEventsRepository.insert(pushEvents)
            case EventType.ReleaseEvent =>
              val releaseEvents: ReleaseEvents = e.asInstanceOf[ReleaseEvents]
              EventsRepository.insert(this.generateEvent(event, Action.created.toString))
              ReleaseEventsRepository.insert(releaseEvents)
            case EventType.WatchEvent =>
              val watchEvents: WatchEvents = e.asInstanceOf[WatchEvents]
              EventsRepository.insert(this.generateEvent(event, Action.created.toString))
              WatchEventsRepository.insert(watchEvents)
            case EventType.Undefined => logger.info("Undefined event skip insert.")
          }
        }
      }
    }
  }

  def convert(jsonList: String): Option[List[EventObject]] = {
    val events: Json = parse(jsonList).getOrElse(Json.Null)
    val hCursor: HCursor = events.hcursor
    val data = for (json <- hCursor.values) yield {
      for (x <- json.toList) yield {
        val eventId: Long = x.hcursor.get[Long]("id").getOrElse(0)
        val eventType: EventType = x.hcursor.get[String]("type").getOrElse("").toEventType
        val userName: String = x.hcursor.downField("actor").get[String]("login").getOrElse("")
        val createdAt: Long = ZonedDateTime.parse(x.hcursor.get[String]("created_at").getOrElse("")).toEpochSecond //TODO: Implement DateTimeParseException
        val repository = this.generateRepositoryObject(x)
        x.hcursor.get[String]("type").getOrElse("").toEventType match {
          case EventType.CreateEvent =>
            EventObject(eventId, eventType, userName, repository, createdAt, generateCreateEventObject(eventId, userName, createdAt, x))
          case EventType.DeleteEvent =>
            EventObject(eventId, eventType, userName, repository, createdAt, generateDeleteEventObject(eventId, userName, createdAt, x))
          case EventType.ForkEvent =>
            EventObject(eventId, eventType, userName, repository, createdAt, generateForkEventObject(eventId, userName, createdAt, repository.get.id))
          case EventType.IssueCommentEvent =>
            EventObject(eventId, eventType, userName, repository, createdAt, generateIssueCommentEventObject(eventId, userName, repository.get.id, createdAt, x))
          case EventType.IssuesEvent =>
            EventObject(eventId, eventType, userName, repository, createdAt, generateIssuesEventObject(eventId, userName, repository.get.id, createdAt, x))
          case EventType.PullRequestEvent =>
            EventObject(eventId, eventType, userName, repository, createdAt, generatePullRequestEventObject(eventId, userName, repository.get.id, createdAt, x))
          case EventType.PullRequestReviewEvent =>
            EventObject(eventId, eventType, userName, repository, createdAt, generatePullRequestReviewEventObject(eventId, userName, repository.get.id, createdAt, x))
          case EventType.PullRequestReviewCommentEvent =>
            EventObject(eventId, eventType, userName, repository, createdAt, generatePullRequestReviewCommentEventObject(eventId, userName, repository.get.id, createdAt, x))
          case EventType.PushEvent =>
            EventObject(eventId, eventType, userName, repository, createdAt, generatePushEventObject(eventId, userName, repository.get.id, createdAt, x))
          case EventType.ReleaseEvent =>
            EventObject(eventId, eventType, userName, repository, createdAt, generateReleaseEventObject(eventId, userName, repository.get.id, createdAt, x))
          case EventType.WatchEvent =>
            EventObject(eventId, eventType, userName, repository, createdAt, generateWatchEventObject(eventId, userName, repository.get.id, createdAt, x))
          case EventType.Undefined => {
            logger.error(s"event id: [$eventId] is undefined event type.")
            //FIXME
            EventObject(eventId, eventType, userName, repository, createdAt, Option(DummyEvent()))
          }
        }
      }
    }
    data
  }

  def generateRepositoryObject(json: Json): Option[Repositories] = {
    val id: Long = json.hcursor.downField("repo").get[Long]("id").getOrElse(0)
    val name: String = json.hcursor.downField("repo").get[String]("name").getOrElse("")

    //FIXME
    if (id != 0 && name != "") {
      Some(Repositories(id, name))
    } else {
      None
    }
  }

  def generateCreateEventObject(eventId: Long, userName: String, createdAt: Long, json: Json): Option[CreateEvents] = {
    val ref: String = json.hcursor.downField("payload").get[String]("ref").getOrElse("")
    val refType: String = json.hcursor.downField("payload").get[String]("ref_type").getOrElse("")

    //FIXME
    if (ref != "" && refType != "") {
      Some(CreateEvents(eventId, userName, refType, ref, createdAt))
    } else {
      None
    }
  }

  def generateDeleteEventObject(eventId: Long, userName: String, createdAt: Long, json: Json): Option[DeleteEvents] = {
    val ref: String = json.hcursor.downField("payload").get[String]("ref").getOrElse("")
    val refType: String = json.hcursor.downField("payload").get[String]("ref_type").getOrElse("")

    //FIXME
    if (ref != "" && refType != "") {
      Some(DeleteEvents(eventId, userName, refType, ref, createdAt))
    } else {
      None
    }
  }

  def generateForkEventObject(eventId: Long, userName: String, createdAt: Long, repositoryId: Long): Option[ForkEvents] = {
    Some(ForkEvents(eventId, userName, repositoryId, createdAt))
  }

  def generateIssueCommentEventObject(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[IssueCommentEvents] = {
    val action: String = json.hcursor.downField("payload").get[String]("action").getOrElse("")
    val issueNumber: Int = json.hcursor.downField("payload").downField("issue").get[Int]("number").getOrElse(0)

    //FIXME
    if (action != "" && issueNumber != 0) {
      Some(IssueCommentEvents(eventId, userName, repositoryId, issueNumber, action, createdAt))
    } else {
      None
    }
  }

  def generateIssuesEventObject(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[IssueEvents] = {
    val action: String = json.hcursor.downField("payload").get[String]("action").getOrElse("")
    val issueNumber: Int = json.hcursor.downField("payload").downField("issue").get[Int]("number").getOrElse(0)

    //FIXME
    if (action != "" && issueNumber != 0) {
      Some(IssueEvents(eventId, userName, repositoryId, issueNumber, action, createdAt))
    } else {
      None
    }
  }

  def generatePullRequestEventObject(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[PullRequestEvents] = {
    val action: String = json.hcursor.downField("payload").get[String]("action").getOrElse("")
    val prNumber: Int = json.hcursor.downField("payload").downField("pull_request").get[Int]("number").getOrElse(0)

    //FIXME
    if (action != "" && prNumber != 0) {
      Some(PullRequestEvents(eventId, userName, repositoryId, prNumber, action, createdAt))
    } else {
      None
    }
  }

  def generatePullRequestReviewEventObject(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[PullRequestReviewEvents] = {
    val action: String = json.hcursor.downField("payload").get[String]("action").getOrElse("")
    val prNumber: Int = json.hcursor.downField("payload").downField("pull_request").get[Int]("number").getOrElse(0)

    //FIXME
    if (action != "" && prNumber != 0) {
      Some(PullRequestReviewEvents(eventId, userName, repositoryId, prNumber, action, createdAt))
    } else {
      None
    }
  }

  def generatePullRequestReviewCommentEventObject(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[PullRequestReviewCommentEvents] = {
    val action: String = json.hcursor.downField("payload").get[String]("action").getOrElse("")
    val prNumber: Int = json.hcursor.downField("payload").downField("pull_request").get[Int]("number").getOrElse(0)

    //FIXME
    if (action != "" && prNumber != 0) {
      Some(PullRequestReviewCommentEvents(eventId, userName, repositoryId, prNumber, action, createdAt))
    } else {
      None
    }
  }

  def generatePushEventObject(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[PushEvents] = {
    val ref: String = json.hcursor.downField("payload").get[String]("ref").getOrElse("")
    val size: Int = json.hcursor.downField("payload").get[Int]("size").getOrElse(0)

    //FIXME
    if (ref != "" && size != 0) {
      Some(PushEvents(eventId, userName, repositoryId, ref, size, createdAt))
    } else {
      None
    }
  }

  def generateReleaseEventObject(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[ReleaseEvents] = {
    val tagName: String = json.hcursor.downField("payload").downField("release").get[String]("tag_name").getOrElse("")
    val name: String = json.hcursor.downField("payload").downField("release").get[String]("name").getOrElse("")
    val action: String = json.hcursor.downField("payload").get[String]("action").getOrElse("")

    //FIXME
    if (tagName != "" && name != "" && action != "") {
      Some(ReleaseEvents(eventId, userName, repositoryId, tagName, name, action, createdAt))
    } else {
      None
    }
  }

  def generateWatchEventObject(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[WatchEvents] = {
    val action: String = json.hcursor.downField("payload").get[String]("action").getOrElse("")

    //FIXME
    if (action != "") {
      Some(WatchEvents(eventId, userName, repositoryId, action, createdAt))
    } else {
      None
    }
  }

}
