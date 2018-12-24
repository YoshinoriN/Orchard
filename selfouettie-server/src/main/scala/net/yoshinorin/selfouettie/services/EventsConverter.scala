package net.yoshinorin.selfouettie.services

import java.time.ZonedDateTime
import io.circe.{Decoder, _}
import io.circe.parser._
import net.yoshinorin.selfouettie.models._
import net.yoshinorin.selfouettie.utils.Converter.eventTypeConverter
import net.yoshinorin.selfouettie.utils.Logger
import net.yoshinorin.selfouettie.types.EventType

trait EventsConverter extends Logger {

  def convert(jsonList: String): Option[eventObject] = {
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

          /*
          x.hcursor.get[String]("type").getOrElse("").toEventType match {
            case EventType.CreateEvent => ???
            case EventType.CreateEvent => ???
            case EventType.ForkEvent => ???
            case EventType.IssueCommentEvent => ???
            case EventType.IssuesEvent => ???
            case EventType.PullRequestEvent => ???
            case EventType.PullRequestReviewEvent => ???
            case EventType.PullRequestReviewCommentEvent => ???
            case EventType.PushEvent => ???
            case EventType.ReleaseEvent => ???
            case EventType.WatchEvent => ???
          }
          */
          //logger.error(s"event id: [$eventId] import failed.")
          eventObject(eventId, eventType, userName, repository, createdAt)
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
    }
    None
  }

  /*
  def generateCreateEvent(json: Json): Option[CreateEvent] = {

  }
  */


  case class eventObject(
    id: Long,
    eventType: EventType,
    userName: String,
    repository: Option[Repository],
    createdAt: Long
    //TODO: Add fields
  )

}
