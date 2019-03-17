package net.yoshinorin.orchard.services.github.event.json

import java.time.ZonedDateTime

import io.circe.{Decoder, Json}
import net.yoshinorin.orchard.definitions.action.DefaultAction
import net.yoshinorin.orchard.models.db.{Events, Repositories}
import net.yoshinorin.orchard.utils.Logger

class Event(repository: Repositories, json: Json) extends JsonBase[Events] with Logger {

  val event: Option[Events] = this.convert

  /**
   * Get Converted case class
   *
   * @return
   */
  def getConvertedCaseClass: Option[Events] = this.event

  /**
   * Convert JSON to Events case class
   *
   * @return
   */
  override def convert: Option[Events] = {
    val id: Decoder.Result[Long] = json.hcursor.get[Long]("id")
    val eventType: Decoder.Result[String] = json.hcursor.get[String]("type")
    val userName: Decoder.Result[String] = json.hcursor.downField("actor").get[String]("login")
    val createdAt: Decoder.Result[String] = json.hcursor.get[String]("created_at")

    if (id.isRight && eventType.isRight && userName.isRight && createdAt.isRight) {
      Some(
        Events(
          0, // HACK: This field apply auto-increment value by DataBase
          id.right.get,
          eventType.right.get,
          userName.right.get,
          repository.id,
          DefaultAction.get(eventType.right.get).value,
          repository.name,
          ZonedDateTime.parse(createdAt.right.get).toEpochSecond
        ))
    } else {
      logger.error("Can not parse Issue")
      None
    }
  }
}

object Event {

  def apply(repository: Repositories, json: Json): Event = new Event(repository, json)

}
