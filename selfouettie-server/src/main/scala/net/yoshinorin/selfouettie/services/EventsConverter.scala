package net.yoshinorin.selfouettie.services

import java.time.ZonedDateTime
import io.circe._
import io.circe.parser._
import net.yoshinorin.selfouettie.models._
import net.yoshinorin.selfouettie.utils.Converter.eventTypeConverter
import net.yoshinorin.selfouettie.utils.Logger
import net.yoshinorin.selfouettie.types.EventType

trait EventsConverter extends Logger {

  def convert(jsonList: String): Option[EventObject] = {
    val events: Json = parse(jsonList).getOrElse(Json.Null)
    val hCursor: HCursor = events.hcursor
    val data = for (json <- hCursor.values) yield {
      json.headOption.map { x =>
        {
          val eventId: Long = x.hcursor.get[Long]("id").getOrElse(0)
          val eventType: EventType = x.hcursor.get[String]("type").getOrElse("").toEventType
          val userName: String = x.hcursor.downField("actor").get[String]("login").getOrElse("")
          val createdAt: Long = ZonedDateTime.parse(x.hcursor.get[String]("created_at").getOrElse("")).toEpochSecond //TODO: Implement DateTimeParseException
          val repository = this.generateRepositoryObject(x)

          x.hcursor.get[String]("type").getOrElse("").toEventType match {
            case EventType.CreateEvent =>
              EventObject(eventId, eventType, userName, repository, createdAt, generateCreateEventObject(eventId, userName, createdAt, x))
            case EventType.ForkEvent =>
              EventObject(eventId, eventType, userName, repository, createdAt, generateForkEventObject(eventId, userName, createdAt, repository.get.id))
            case EventType.IssueCommentEvent =>
              EventObject(eventId, eventType, userName, repository, createdAt, generateIssueCommentEventObject(eventId, userName, createdAt, repository.get.id, x))
            case EventType.IssuesEvent =>
              EventObject(eventId, eventType, userName, repository, createdAt, generateIssuesEventObject(eventId, userName, createdAt, repository.get.id, x))
            case EventType.PullRequestEvent =>
              EventObject(eventId, eventType, userName, repository, createdAt, generatePullRequestEventObject(eventId, userName, createdAt, repository.get.id, x))
            case EventType.PullRequestReviewEvent =>
              EventObject(eventId, eventType, userName, repository, createdAt, generatePullRequestReviewEventObject(eventId, userName, createdAt, repository.get.id, x))
            case EventType.PullRequestReviewCommentEvent =>
              EventObject(eventId, eventType, userName, repository, createdAt, generatePullRequestReviewCommentEventObject(eventId, userName, createdAt, repository.get.id, x))
            case EventType.PushEvent =>
              EventObject(eventId, eventType, userName, repository, createdAt, generatePushEventObject(eventId, userName, createdAt, repository.get.id, x))
            case EventType.ReleaseEvent =>
              EventObject(eventId, eventType, userName, repository, createdAt, generateReleaseEventObject(eventId, userName, createdAt, repository.get.id, x))
            case EventType.WatchEvent =>
              EventObject(eventId, eventType, userName, repository, createdAt, generateWatchEventObject(eventId, userName, createdAt, repository.get.id, x))
            case _ => {
              logger.error(s"event id: [$eventId] is undefined event type.")
              //FIXME
              EventObject(eventId, eventType, userName, repository, createdAt, Option(DummyEvent()))
            }
          }
        }
      }
    }
    data match {
      case Some(x) => x
      case None => None
    }
  }

  def generateRepositoryObject(json: Json): Option[Repository] = {
    val id: Long = json.hcursor.downField("repo").get[Long]("id").getOrElse(0)
    val name: String = json.hcursor.downField("repo").get[String]("name").getOrElse("")

    //FIXME
    if (id != 0 && name != "") {
      Some(Repository(id, name))
    } else {
      None
    }
  }

  def generateCreateEventObject(eventId: Long, userName: String, createdAt: Long, json: Json): Option[CreateEvent] = {
    val ref: String = json.hcursor.downField("payload").get[String]("ref").getOrElse("")
    val refType: String = json.hcursor.downField("payload").get[String]("ref_type").getOrElse("")

    //FIXME
    if (ref != "" && refType != "") {
      Some(CreateEvent(eventId, userName, refType, ref, createdAt))
    } else {
      None
    }
  }

  def generateForkEventObject(eventId: Long, userName: String, createdAt: Long, repositoryId: Long): Option[ForkEvent] = {
    Some(ForkEvent(eventId, userName, repositoryId, createdAt))
  }

  def generateIssueCommentEventObject(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[IssueCommentEvent] = {
    val action: String = json.hcursor.downField("payload").get[String]("action").getOrElse("")
    val issueNumber: Long = json.hcursor.downField("payload").downField("issue").get[Long]("number").getOrElse(0)

    //FIXME
    if (action != "" && issueNumber != 0) {
      Some(IssueCommentEvent(eventId, userName, repositoryId, issueNumber, action, createdAt))
    } else {
      None
    }
  }

  def generateIssuesEventObject(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[IssuesEvent] = {
    val action: String = json.hcursor.downField("payload").get[String]("action").getOrElse("")
    val issueNumber: Long = json.hcursor.downField("payload").downField("issue").get[Long]("number").getOrElse(0)

    //FIXME
    if (action != "" && issueNumber != 0) {
      Some(IssuesEvent(eventId, userName, repositoryId, issueNumber, action, createdAt))
    } else {
      None
    }
  }

  def generatePullRequestEventObject(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[PullRequestEvent] = {
    val action: String = json.hcursor.downField("payload").get[String]("action").getOrElse("")
    val prNumber: Long = json.hcursor.downField("payload").downField("pull_request").get[Long]("number").getOrElse(0)

    //FIXME
    if (action != "" && prNumber != 0) {
      Some(PullRequestEvent(eventId, userName, repositoryId, prNumber, action, createdAt))
    } else {
      None
    }
  }

  def generatePullRequestReviewEventObject(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[PullRequestReviewEvent] = {
    val action: String = json.hcursor.downField("payload").get[String]("action").getOrElse("")
    val prNumber: Long = json.hcursor.downField("payload").downField("pull_request").get[Long]("number").getOrElse(0)

    //FIXME
    if (action != "" && prNumber != 0) {
      Some(PullRequestReviewEvent(eventId, userName, repositoryId, prNumber, action, createdAt))
    } else {
      None
    }
  }

  def generatePullRequestReviewCommentEventObject(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[PullRequestReviewCommentEvent] = {
    val action: String = json.hcursor.downField("payload").get[String]("action").getOrElse("")
    val prNumber: Long = json.hcursor.downField("payload").downField("pull_request").get[Long]("number").getOrElse(0)

    //FIXME
    if (action != "" && prNumber != 0) {
      Some(PullRequestReviewCommentEvent(eventId, userName, repositoryId, prNumber, action, createdAt))
    } else {
      None
    }
  }

  def generatePushEventObject(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[PushEvent] = {
    val ref: String = json.hcursor.downField("payload").get[String]("ref").getOrElse("")
    val size: Int = json.hcursor.downField("payload").get[Int]("size").getOrElse(0)

    //FIXME
    if (ref != "" && size != 0) {
      Some(PushEvent(eventId, userName, repositoryId, ref, size, createdAt))
    } else {
      None
    }
  }

  def generateReleaseEventObject(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[ReleaseEvent] = {
    val tagName: String = json.hcursor.downField("payload").downField("release").get[String]("tag_name").getOrElse("")
    val name: String = json.hcursor.downField("payload").downField("release").get[String]("name").getOrElse("")
    val action: String = json.hcursor.downField("payload").get[String]("action").getOrElse("")

    //FIXME
    if (tagName != "" && name != "" && action != "") {
      Some(ReleaseEvent(eventId, userName, repositoryId, tagName, name, action, createdAt))
    } else {
      None
    }
  }

  def generateWatchEventObject(eventId: Long, userName: String, repositoryId: Long, createdAt: Long, json: Json): Option[WatchEvent] = {
    val action: String = json.hcursor.downField("payload").get[String]("action").getOrElse("")

    //FIXME
    if (action != "") {
      Some(WatchEvent(eventId, userName, repositoryId, action, createdAt))
    } else {
      None
    }
  }

}
