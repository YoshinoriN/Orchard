package net.yoshinorin.orchard.services

import java.time.ZonedDateTime
import io.circe.parser.parse
import io.circe.{Decoder, DecodingFailure, HCursor, Json}
import net.yoshinorin.orchard.models._
import net.yoshinorin.orchard.models.db._
import net.yoshinorin.orchard.types.{ActionType, EventType}
import net.yoshinorin.orchard.utils.Converter.eventTypeConverter
import net.yoshinorin.orchard.utils.Logger

object EventService extends QuillProvider with Logger {

  import ctx._;

  private def generateEvent(event: EventObject, action: String): Events = {
    Events(event.id, event.eventType.value, event.userName, event.repository.id, action, event.repository.name, event.createdAt)
  }

  def create(event: EventObject): Unit = {

    transaction {
      UsersRepository.insert(Users(event.userName, event.createdAt))
      RepositoriesRepository.insert(event.repository)

      val eventType = event.eventType
      event.event match {
        case None => logger.info("skip insert event detail. event data is nothing.")
        case Some(e) => {
          eventType match {
            case EventType.CreateEvent =>
              EventsRepository.insert(this.generateEvent(event, ActionType.Created.toString))
              CreateEventsRepository.insert(e.asInstanceOf[CreateEvents])
            case EventType.DeleteEvent =>
              EventsRepository.insert(this.generateEvent(event, ActionType.Created.toString))
              DeleteEventsRepository.insert(e.asInstanceOf[DeleteEvents])
            case EventType.ForkEvent =>
              EventsRepository.insert(this.generateEvent(event, ActionType.Fork.toString))
              ForkEventsRepository.insert(e.asInstanceOf[ForkEvents])
            case EventType.IssueCommentEvent =>
              val issueCommentEvents: IssueCommentEvents = e.asInstanceOf[IssueCommentEvents]
              EventsRepository.insert(this.generateEvent(event, issueCommentEvents.action))
              IssuesRepository.insert(Issues(event.repository.id, issueCommentEvents.issueNumber, "TODO"))
              IssueCommentEventsRepository.insert(issueCommentEvents)
            case EventType.IssuesEvent =>
              val issuesEvent: IssueEvents = e.asInstanceOf[IssueEvents]
              IssuesRepository.insert(Issues(event.repository.id, issuesEvent.issueNumber, "TODO"))
              EventsRepository.insert(this.generateEvent(event, ActionType.Created.toString))
              IssuesEventsRepository.insert(issuesEvent)
            case EventType.PullRequestEvent =>
              val pullRequestEvents: PullRequestEvents = e.asInstanceOf[PullRequestEvents]
              EventsRepository.insert(this.generateEvent(event, ActionType.Created.toString))
              PullRequestsRepository.insert(PullRequests(event.repository.id, pullRequestEvents.pullRequestNumber, "TODO", false)) //TODO: update merged Boolean
              PullRequestEventsRepository.insert(pullRequestEvents)
            case EventType.PullRequestReviewEvent =>
              val pullRequestReviewEvents: PullRequestReviewEvents = e.asInstanceOf[PullRequestReviewEvents]
              EventsRepository.insert(this.generateEvent(event, ActionType.Created.toString))
              PullRequestsRepository.insert(PullRequests(event.repository.id, pullRequestReviewEvents.pullRequestNumber, "TODO", false)) //TODO: update merged Boolean
              PullRequestReviewEventsRepository.insert(pullRequestReviewEvents)
            case EventType.PullRequestReviewCommentEvent =>
              val pullRequestReviewCommentEvents: PullRequestReviewCommentEvents = e.asInstanceOf[PullRequestReviewCommentEvents]
              EventsRepository.insert(this.generateEvent(event, ActionType.Created.toString))
              PullRequestsRepository.insert(PullRequests(event.repository.id, pullRequestReviewCommentEvents.pullRequestNumber, "TODO", false)) //TODO: update merged Boolean
              PullRequestReviewCommentEventsRepository.insert(pullRequestReviewCommentEvents)
            case EventType.PushEvent =>
              val pushEvents: PushEvents = e.asInstanceOf[PushEvents]
              EventsRepository.insert(this.generateEvent(event, ActionType.Created.toString))
              PushEventsRepository.insert(pushEvents)
            case EventType.ReleaseEvent =>
              val releaseEvents: ReleaseEvents = e.asInstanceOf[ReleaseEvents]
              EventsRepository.insert(this.generateEvent(event, ActionType.Created.toString))
              ReleaseEventsRepository.insert(releaseEvents)
            case EventType.WatchEvent =>
              val watchEvents: WatchEvents = e.asInstanceOf[WatchEvents]
              EventsRepository.insert(this.generateEvent(event, ActionType.Created.toString))
              WatchEventsRepository.insert(watchEvents)
            case EventType.Undefined => logger.info("Undefined event skip insert.")
          }
        }
      }
    }
  }

  /**
   * Parse GitHub events to EventObject
   *
   * @param jsonList GitHub events JSON
   * @return parsed object
   */
  def convert(jsonList: String): Option[List[EventObject]] = {
    val events: Json = parse(jsonList).getOrElse(Json.Null)
    val hCursor: HCursor = events.hcursor
    val data = for (json <- hCursor.values) yield {
      for (x <- json.toList) yield {

        val mayBeEventId: Either[DecodingFailure, Long] = x.hcursor.get[Long]("id") match {
          case Right(r) => Right(r)
          case Left(l) => {
            logger.error("Can not parse event id.")
            Left(l)
          }
        }

        val mayBeEventType: Either[DecodingFailure, EventType] = x.hcursor.get[String]("type") match {
          case Right(r) => Right(r.toEventType)
          case Left(l) => {
            logger.error("Can not parse event type.")
            Left(l)
          }
        }

        val mayBeUserName: Either[DecodingFailure, String] = x.hcursor.downField("actor").get[String]("login") match {
          case Right(r) => Right(r)
          case Left(l) => {
            logger.error("Can not parse userName.")
            Left(l)
          }
        }

        val mayBeCreatedAt: Either[DecodingFailure, Long] = x.hcursor.get[String]("created_at") match {
          case Right(r) => Right(ZonedDateTime.parse(r).toEpochSecond)
          case Left(l) => {
            logger.error("Can not parse createdAt.")
            Left(l)
          }
        }

        val mayBeRepository = this.generateRepository(x)

        if (mayBeEventId.isLeft || mayBeEventType.isLeft || mayBeUserName.isLeft || mayBeCreatedAt.isLeft || mayBeRepository.isEmpty) {
          return None
        } else {
          val eventId = mayBeEventId.right.get
          val eventType = mayBeEventType.right.get
          val userName = mayBeUserName.right.get
          val createdAt = mayBeCreatedAt.right.get
          val repository = mayBeRepository.get

          eventType match {
            case EventType.CreateEvent =>
              val createEvent: Option[CreateEvents] = generateCreateEvent(eventId, userName, createdAt, x)
              EventObject(eventId, eventType, userName, repository, createdAt, createEvent)
            case EventType.DeleteEvent =>
              val deleteEvent: Option[DeleteEvents] = generateDeleteEvent(eventId, userName, createdAt, x)
              EventObject(eventId, eventType, userName, repository, createdAt, deleteEvent)
            case EventType.ForkEvent =>
              val forkEvent: Option[ForkEvents] = generateForkEvent(eventId, userName, createdAt, repository.id)
              EventObject(eventId, eventType, userName, repository, createdAt, forkEvent)
            case EventType.IssueCommentEvent =>
              val issueCommentEvent: Option[IssueCommentEvents] = generateIssueCommentEvent(eventId, userName, repository.id, createdAt, x)
              EventObject(eventId, eventType, userName, repository, createdAt, issueCommentEvent)
            case EventType.IssuesEvent =>
              val issueEvent: Option[IssueEvents] = generateIssuesEvent(eventId, userName, repository.id, createdAt, x)
              EventObject(eventId, eventType, userName, repository, createdAt, issueEvent)
            case EventType.PullRequestEvent =>
              val pullRequestEvent: Option[PullRequestEvents] = generatePullRequestEvent(eventId, userName, repository.id, createdAt, x)
              EventObject(eventId, eventType, userName, repository, createdAt, pullRequestEvent)
            case EventType.PullRequestReviewEvent =>
              val pullRequestReviewEvent: Option[PullRequestReviewEvents] = generatePullRequestReviewEvent(eventId, userName, repository.id, createdAt, x)
              EventObject(eventId, eventType, userName, repository, createdAt, pullRequestReviewEvent)
            case EventType.PullRequestReviewCommentEvent =>
              val pullRequestReviewCommentEvent: Option[PullRequestReviewCommentEvents] = generatePullRequestReviewCommentEvent(eventId, userName, repository.id, createdAt, x)
              EventObject(eventId, eventType, userName, repository, createdAt, pullRequestReviewCommentEvent)
            case EventType.PushEvent =>
              val pushEvent: Option[PushEvents] = generatePushEvent(eventId, userName, repository.id, createdAt, x)
              EventObject(eventId, eventType, userName, repository, createdAt, pushEvent)
            case EventType.ReleaseEvent =>
              val releaseEvent: Option[ReleaseEvents] = generateReleaseEvent(eventId, userName, repository.id, createdAt, x)
              EventObject(eventId, eventType, userName, repository, createdAt, releaseEvent)
            case EventType.WatchEvent =>
              val watchEvent: Option[WatchEvents] = generateWatchEvent(eventId, userName, repository.id, createdAt, x)
              EventObject(eventId, eventType, userName, repository, createdAt, watchEvent)
            case EventType.Undefined => {
              logger.error(s"event id: [$eventId] is undefined event type.")
              //FIXME
              EventObject(eventId, eventType, userName, repository, createdAt, Option(DummyEvent()))
            }
          }
        }
      }
    }
    data
  }

  /**
   * Generate Repository case class from JSON
   *
   * @param json GitHub Event JSON
   * @return
   */
  private def generateRepository(json: Json): Option[Repositories] = {
    val id: Decoder.Result[Long] = json.hcursor.downField("repo").get[Long]("id")
    val name: Decoder.Result[String] = json.hcursor.downField("repo").get[String]("name")

    if (id.isRight && name.isRight) {
      Some(Repositories(id.right.get, name.right.get))
    } else {
      logger.error("Can not parse Repository.")
      None
    }
  }

  /**
   * Generate CreateEvents case class from JSON
   *
   * @param eventId event id
   * @param userName user name
   * @param createdAt created at (epoch second)
   * @param json GitHub Event JSON
   * @return
   */
  private def generateCreateEvent(eventId: Long, userName: String, createdAt: Long, json: Json): Option[CreateEvents] = {
    val ref: Decoder.Result[String] = json.hcursor.downField("payload").get[String]("ref")
    val refType: Decoder.Result[String] = json.hcursor.downField("payload").get[String]("ref_type")

    if (ref.isRight && refType.isRight) {
      Some(CreateEvents(eventId, userName, refType.right.get, ref.right.get, createdAt))
    } else {
      logger.error(s"Event id [${eventId}]. Failed parse to CreateEvents.")
      None
    }
  }

  /**
   * Generate DeleteEvents case class from JSON
   *
   * @param eventId event id
   * @param userName user name
   * @param createdAt created at (epoch second)
   * @param json GitHub Event JSON
   * @return
   */
  private def generateDeleteEvent(eventId: Long, userName: String, createdAt: Long, json: Json): Option[DeleteEvents] = {
    val ref: Decoder.Result[String] = json.hcursor.downField("payload").get[String]("ref")
    val refType: Decoder.Result[String] = json.hcursor.downField("payload").get[String]("ref_type")

    if (ref.isRight && refType.isRight) {
      Some(DeleteEvents(eventId, userName, refType.right.get, ref.right.get, createdAt))
    } else {
      logger.error(s"Event id [${eventId}]. Failed parse to DeleteEvents.")
      None
    }
  }

  /**
   * Generate ForkEvents case class from JSON
   *
   * @param eventId event id
   * @param userName user name
   * @param createdAt created at (epoch second)
   * @param repositoryId repository id
   * @return
   */
  private def generateForkEvent(eventId: Long, userName: String, createdAt: Long, repositoryId: Long): Option[ForkEvents] = {
    Some(ForkEvents(eventId, userName, repositoryId, createdAt))
  }

  /**
   * Generate IssueCommentEvents case class from JSON
   *
   * @param eventId event id
   * @param userName user name
   * @param createdAt created at (epoch second)
   * @param json GitHub Event JSON
   * @return
   */
  private def generateIssueCommentEvent(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[IssueCommentEvents] = {
    val action: Decoder.Result[String] = json.hcursor.downField("payload").get[String]("action")
    val issueNumber: Decoder.Result[Int] = json.hcursor.downField("payload").downField("issue").get[Int]("number")

    if (action.isRight && issueNumber.isRight) {
      Some(IssueCommentEvents(eventId, userName, repositoryId, issueNumber.right.get, action.right.get, createdAt))
    } else {
      logger.error(s"Event id [${eventId}]. Failed parse to IssueCommentEvents.")
      None
    }
  }

  /**
   * Generate IssueEvents case class from JSON
   *
   * @param eventId event id
   * @param userName user name
   * @param createdAt created at (epoch second)
   * @param json GitHub Event JSON
   * @return
   */
  private def generateIssuesEvent(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[IssueEvents] = {
    val action: Decoder.Result[String] = json.hcursor.downField("payload").get[String]("action")
    val issueNumber: Decoder.Result[Int] = json.hcursor.downField("payload").downField("issue").get[Int]("number")

    if (action.isRight && issueNumber.isRight) {
      Some(IssueEvents(eventId, userName, repositoryId, issueNumber.right.get, action.right.get, createdAt))
    } else {
      logger.error(s"Event id [${eventId}]. Failed parse to IssueEvents.")
      None
    }
  }

  /**
   * Generate PullRequestEvents case class from JSON
   *
   * @param eventId event id
   * @param userName user name
   * @param createdAt created at (epoch second)
   * @param json GitHub Event JSON
   * @return
   */
  private def generatePullRequestEvent(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[PullRequestEvents] = {
    val action: Decoder.Result[String] = json.hcursor.downField("payload").get[String]("action")
    val prNumber: Decoder.Result[Int] = json.hcursor.downField("payload").downField("pull_request").get[Int]("number")

    if (action.isRight && prNumber.isRight) {
      Some(PullRequestEvents(eventId, userName, repositoryId, prNumber.right.get, action.right.get, createdAt))
    } else {
      logger.error(s"Event id [${eventId}]. Failed parse to PullRequestEvents.")
      None
    }
  }

  /**
   * Generate PullRequestReviewEvents case class from JSON
   *
   * @param eventId event id
   * @param userName user name
   * @param createdAt created at (epoch second)
   * @param json GitHub Event JSON
   * @return
   */
  private def generatePullRequestReviewEvent(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[PullRequestReviewEvents] = {
    val action: Decoder.Result[String] = json.hcursor.downField("payload").get[String]("action")
    val prNumber: Decoder.Result[Int] = json.hcursor.downField("payload").downField("pull_request").get[Int]("number")

    if (action.isRight && prNumber.isRight) {
      Some(PullRequestReviewEvents(eventId, userName, repositoryId, prNumber.right.get, action.right.get, createdAt))
    } else {
      logger.error(s"Event id [${eventId}]. Failed parse to PullRequestReviewEvents.")
      None
    }
  }

  /**
   * Generate PullRequestReviewCommentEvents case class from JSON
   *
   * @param eventId event id
   * @param userName user name
   * @param createdAt created at (epoch second)
   * @param json GitHub Event JSON
   * @return
   */
  private def generatePullRequestReviewCommentEvent(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[PullRequestReviewCommentEvents] = {
    val action: Decoder.Result[String] = json.hcursor.downField("payload").get[String]("action")
    val prNumber: Decoder.Result[Int] = json.hcursor.downField("payload").downField("pull_request").get[Int]("number")

    if (action.isRight && prNumber.isRight) {
      Some(PullRequestReviewCommentEvents(eventId, userName, repositoryId, prNumber.right.get, action.right.get, createdAt))
    } else {
      logger.error(s"Event id [${eventId}]. Failed parse to PullRequestReviewCommentEvents.")
      None
    }
  }

  /**
   * Generate PushEvents case class from JSON
   *
   * @param eventId event id
   * @param userName user name
   * @param createdAt created at (epoch second)
   * @param json GitHub Event JSON
   * @return
   */
  private def generatePushEvent(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[PushEvents] = {
    val ref: Decoder.Result[String] = json.hcursor.downField("payload").get[String]("ref")
    val size: Decoder.Result[Int] = json.hcursor.downField("payload").get[Int]("size")

    if (ref.isRight && size.isRight) {
      Some(PushEvents(eventId, userName, repositoryId, ref.right.get, size.right.get, createdAt))
    } else {
      logger.error(s"Event id [${eventId}]. Failed parse to PushEvents.")
      None
    }
  }

  /**
   * Generate ReleaseEvents case class from JSON
   *
   * @param eventId event id
   * @param userName user name
   * @param createdAt created at (epoch second)
   * @param json GitHub Event JSON
   * @return
   */
  private def generateReleaseEvent(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[ReleaseEvents] = {
    val tagName: Decoder.Result[String] = json.hcursor.downField("payload").downField("release").get[String]("tag_name")
    val name: Decoder.Result[String] = json.hcursor.downField("payload").downField("release").get[String]("name")
    val action: Decoder.Result[String] = json.hcursor.downField("payload").get[String]("action")

    if (tagName.isRight && name.isRight && action.isRight) {
      Some(ReleaseEvents(eventId, userName, repositoryId, tagName.right.get, name.right.get, action.right.get, createdAt))
    } else {
      logger.error(s"Event id [${eventId}]. Failed parse to ReleaseEvents.")
      None
    }
  }

  /**
   * Generate WatchEvents case class from JSON
   *
   * @param eventId event id
   * @param userName user name
   * @param createdAt created at (epoch second)
   * @param json GitHub Event JSON
   * @return
   */
  private def generateWatchEvent(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[WatchEvents] = {
    val action: Decoder.Result[String] = json.hcursor.downField("payload").get[String]("action")

    if (action.isRight) {
      Some(WatchEvents(eventId, userName, repositoryId, action.right.get, createdAt))
    } else {
      logger.error(s"Event id [${eventId}]. Failed parse to WatchEvents.")
      None
    }
  }

}
