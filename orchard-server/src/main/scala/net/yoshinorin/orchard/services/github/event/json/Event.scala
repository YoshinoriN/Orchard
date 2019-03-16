package net.yoshinorin.orchard.services.github.event.json

import java.time.ZonedDateTime

import io.circe.{Decoder, Json}
import net.yoshinorin.orchard.definitions.action.{ActionType, DefaultAction}
import net.yoshinorin.orchard.models.db.Events
import net.yoshinorin.orchard.utils.Logger

class Event(repository: Repository, jsonString: String) extends JsonBase[Events] with Logger {

  val parsedJson: Json = this.parse(jsonString)

  val event: Option[Events] = this.convert

  /**
   * Get Converted case class
   *
   * @return
   */
  override def getConvertedCaseClass: Option[Events] = this.event

  /**
   * Convert JSON to Events case class
   *
   * @return
   */
  override def convert: Option[Events] = {
    val id: Decoder.Result[Long] = parsedJson.hcursor.get[Long]("id")
    val eventType: Decoder.Result[String] = parsedJson.hcursor.get[String]("type")
    val userName: Decoder.Result[String] = parsedJson.hcursor.downField("actor").get[String]("login")
    val createdAt: Decoder.Result[String] = parsedJson.hcursor.get[String]("created_at")

    if (id.isRight && eventType.isRight && userName.isRight && repository.repository.isDefined && createdAt.isRight) {
      Some(
        Events(
          id.right.get,
          eventType.right.get,
          userName.right.get,
          repository.repository.get.id,
          DefaultAction.get(eventType.right.get).toString,
          repository.repository.get.name,
          ZonedDateTime.parse(createdAt.right.get).toEpochSecond
        ))
    } else {
      logger.error("Can not parse Issue")
      None
    }
  }
}

object Event {

  def apply(repository: Repository, jsonString: String): Event = new Event(repository, jsonString)

}
