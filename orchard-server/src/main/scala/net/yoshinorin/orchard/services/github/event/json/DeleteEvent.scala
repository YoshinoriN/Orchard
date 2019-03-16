package net.yoshinorin.orchard.services.github.event.json

import io.circe.{Decoder, Json}
import net.yoshinorin.orchard.models.db.{DeleteEvents, Events}
import net.yoshinorin.orchard.utils.Logger

class DeleteEvent(event: Events, json: Json) extends JsonBase[DeleteEvents] with EventBase with Logger {

  val deleteEvent: Option[DeleteEvents] = this.convert

  /**
   * Get Converted case class
   *
   * @return
   */
  override def getConvertedCaseClass: Option[DeleteEvents] = this.deleteEvent

  /**
   * Convert JSON to DeleteEvent case class
   *
   * @return
   */
  override def convert: Option[DeleteEvents] = {
    val ref: Decoder.Result[String] = json.hcursor.downField("payload").get[String]("ref")
    val refType: Decoder.Result[String] = json.hcursor.downField("payload").get[String]("ref_type")

    if (ref.isRight && refType.isRight) {
      Some(DeleteEvents(event.id, event.userName, refType.right.get, ref.right.get, event.createdAt))
    } else {
      logger.error(s"Event id [${event.id}]. Failed parse to CreateEvents.")
      None
    }
  }
}

object DeleteEvent {

  def apply(event: Events, json: Json): DeleteEvent = new DeleteEvent(event, json)

}
