package net.yoshinorin.selfouettie.services

import java.time.ZonedDateTime
import io.circe.parser.parse
import io.circe.{Decoder, DecodingFailure, HCursor, Json}
import net.yoshinorin.selfouettie.models._
import net.yoshinorin.selfouettie.models.db._
import net.yoshinorin.selfouettie.types.{Action, EventType}
import net.yoshinorin.selfouettie.utils.Converter.eventTypeConverter
import net.yoshinorin.selfouettie.utils.Logger

trait EventService extends QuillProvider with Logger {

  import ctx._;

  private[this] def generateEventCaseClass(event: EventObject, action: String): Events = {
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
              EventsRepository.insert(this.generateEventCaseClass(event, Action.created.toString))
              CreateEventsRepository.insert(e.asInstanceOf[CreateEvents])
            case EventType.DeleteEvent =>
              EventsRepository.insert(this.generateEventCaseClass(event, Action.created.toString))
              DeleteEventsRepository.insert(e.asInstanceOf[DeleteEvents])
            case EventType.ForkEvent =>
              EventsRepository.insert(this.generateEventCaseClass(event, Action.fork.toString))
              ForkEventsRepository.insert(e.asInstanceOf[ForkEvents])
            case EventType.IssueCommentEvent =>
              val issueCommentEvents: IssueCommentEvents = e.asInstanceOf[IssueCommentEvents]
              EventsRepository.insert(this.generateEventCaseClass(event, issueCommentEvents.action))
              IssuesRepository.insert(Issues(event.repository.id, issueCommentEvents.issueNumber, "TODO"))
              IssueCommentEventsRepository.insert(issueCommentEvents)
            case EventType.IssuesEvent =>
              val issuesEvent: IssueEvents = e.asInstanceOf[IssueEvents]
              IssuesRepository.insert(Issues(event.repository.id, issuesEvent.issueNumber, "TODO"))
              EventsRepository.insert(this.generateEventCaseClass(event, Action.created.toString))
              IssuesEventsRepository.insert(issuesEvent)
            case EventType.PullRequestEvent =>
              val pullRequestEvents: PullRequestEvents = e.asInstanceOf[PullRequestEvents]
              EventsRepository.insert(this.generateEventCaseClass(event, Action.created.toString))
              PullRequestsRepository.insert(PullRequests(event.repository.id, pullRequestEvents.pullRequestNumber, "TODO", false)) //TODO: update merged Boolean
              PullRequestEventsRepository.insert(pullRequestEvents)
            case EventType.PullRequestReviewEvent =>
              val pullRequestReviewEvents: PullRequestReviewEvents = e.asInstanceOf[PullRequestReviewEvents]
              EventsRepository.insert(this.generateEventCaseClass(event, Action.created.toString))
              PullRequestsRepository.insert(PullRequests(event.repository.id, pullRequestReviewEvents.pullRequestNumber, "TODO", false)) //TODO: update merged Boolean
              PullRequestReviewEventsRepository.insert(pullRequestReviewEvents)
            case EventType.PullRequestReviewCommentEvent =>
              val pullRequestReviewCommentEvents: PullRequestReviewCommentEvents = e.asInstanceOf[PullRequestReviewCommentEvents]
              EventsRepository.insert(this.generateEventCaseClass(event, Action.created.toString))
              PullRequestsRepository.insert(PullRequests(event.repository.id, pullRequestReviewCommentEvents.pullRequestNumber, "TODO", false)) //TODO: update merged Boolean
              PullRequestReviewCommentEventsRepository.insert(pullRequestReviewCommentEvents)
            case EventType.PushEvent =>
              val pushEvents: PushEvents = e.asInstanceOf[PushEvents]
              EventsRepository.insert(this.generateEventCaseClass(event, Action.created.toString))
              PushEventsRepository.insert(pushEvents)
            case EventType.ReleaseEvent =>
              val releaseEvents: ReleaseEvents = e.asInstanceOf[ReleaseEvents]
              EventsRepository.insert(this.generateEventCaseClass(event, Action.created.toString))
              ReleaseEventsRepository.insert(releaseEvents)
            case EventType.WatchEvent =>
              val watchEvents: WatchEvents = e.asInstanceOf[WatchEvents]
              EventsRepository.insert(this.generateEventCaseClass(event, Action.created.toString))
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

        val mayBeRepository = this.generateRepositoryCaseClass(x)

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
              EventObject(eventId, eventType, userName, repository, createdAt, generateCreateEventCaseClass(eventId, userName, createdAt, x))
            case EventType.DeleteEvent =>
              EventObject(eventId, eventType, userName, repository, createdAt, generateDeleteEventCaseClass(eventId, userName, createdAt, x))
            case EventType.ForkEvent =>
              EventObject(eventId, eventType, userName, repository, createdAt, generateForkEventCaseClass(eventId, userName, createdAt, repository.id))
            case EventType.IssueCommentEvent =>
              EventObject(eventId, eventType, userName, repository, createdAt, generateIssueCommentEventCaseClass(eventId, userName, repository.id, createdAt, x))
            case EventType.IssuesEvent =>
              EventObject(eventId, eventType, userName, repository, createdAt, generateIssuesEventCaseClass(eventId, userName, repository.id, createdAt, x))
            case EventType.PullRequestEvent =>
              EventObject(eventId, eventType, userName, repository, createdAt, generatePullRequestEventCaseClass(eventId, userName, repository.id, createdAt, x))
            case EventType.PullRequestReviewEvent =>
              EventObject(eventId, eventType, userName, repository, createdAt, generatePullRequestReviewEventCaseClass(eventId, userName, repository.id, createdAt, x))
            case EventType.PullRequestReviewCommentEvent =>
              EventObject(eventId, eventType, userName, repository, createdAt, generatePullRequestReviewCommentEventCaseClass(eventId, userName, repository.id, createdAt, x))
            case EventType.PushEvent =>
              EventObject(eventId, eventType, userName, repository, createdAt, generatePushEventCaseClass(eventId, userName, repository.id, createdAt, x))
            case EventType.ReleaseEvent =>
              EventObject(eventId, eventType, userName, repository, createdAt, generateReleaseEventCaseClass(eventId, userName, repository.id, createdAt, x))
            case EventType.WatchEvent =>
              EventObject(eventId, eventType, userName, repository, createdAt, generateWatchEventCaseClass(eventId, userName, repository.id, createdAt, x))
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
  private[this] def generateRepositoryCaseClass(json: Json): Option[Repositories] = {
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
  private[this] def generateCreateEventCaseClass(eventId: Long, userName: String, createdAt: Long, json: Json): Option[CreateEvents] = {
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
  private[this] def generateDeleteEventCaseClass(eventId: Long, userName: String, createdAt: Long, json: Json): Option[DeleteEvents] = {
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
   * @param json GitHub Event JSON
   * @return
   */
  private[this] def generateForkEventCaseClass(eventId: Long, userName: String, createdAt: Long, repositoryId: Long): Option[ForkEvents] = {
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
  private[this] def generateIssueCommentEventCaseClass(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[IssueCommentEvents] = {
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
  private[this] def generateIssuesEventCaseClass(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[IssueEvents] = {
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
  private[this] def generatePullRequestEventCaseClass(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[PullRequestEvents] = {
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
  private[this] def generatePullRequestReviewEventCaseClass(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[PullRequestReviewEvents] = {
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
  private[this] def generatePullRequestReviewCommentEventCaseClass(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[PullRequestReviewCommentEvents] = {
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
  private[this] def generatePushEventCaseClass(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[PushEvents] = {
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
  private[this] def generateReleaseEventCaseClass(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[ReleaseEvents] = {
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
  private[this] def generateWatchEventCaseClass(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[WatchEvents] = {
    val action: Decoder.Result[String] = json.hcursor.downField("payload").get[String]("action")

    if (action.isRight) {
      Some(WatchEvents(eventId, userName, repositoryId, action.right.get, createdAt))
    } else {
      logger.error(s"Event id [${eventId}]. Failed parse to WatchEvents.")
      None
    }
  }

}
