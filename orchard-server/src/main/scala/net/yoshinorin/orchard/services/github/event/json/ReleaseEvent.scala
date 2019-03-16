package net.yoshinorin.orchard.services.github.event.json

import io.circe.{Decoder, Json}
import net.yoshinorin.orchard.models.db.{Events, ReleaseEvents}
import net.yoshinorin.orchard.utils.Logger

class ReleaseEvent(event: Events, json: Json) extends JsonBase[ReleaseEvents] with Logger {

  val releaseEvent: Option[ReleaseEvents] = this.convert

  /**
   * Get Converted case class
   *
   * @return
   */
  override def getConvertedCaseClass: Option[ReleaseEvents] = this.releaseEvent

  /**
   * Convert JSON to ReleaseEvents case class
   *
   * @return
   */
  override def convert: Option[ReleaseEvents] = {
    val tagName: Decoder.Result[String] = json.hcursor.downField("payload").downField("release").get[String]("tag_name")
    val name: Decoder.Result[String] = json.hcursor.downField("payload").downField("release").get[String]("name")
    val action: Decoder.Result[String] = json.hcursor.downField("payload").get[String]("action")

    if (tagName.isRight && name.isRight && action.isRight) {
      Some(ReleaseEvents(event.id, event.userName, event.repositoryId, tagName.right.get, name.right.get, action.right.get, event.createdAt))
    } else {
      logger.error(s"Event id [${event.id}]. Failed parse to CreateEvents.")
      None
    }
  }
}

object ReleaseEvent {

  def apply(event: Events, json: Json): ReleaseEvent = new ReleaseEvent(event, json)

}
