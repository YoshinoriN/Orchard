package net.yoshinorin.orchard.services.github.event.json

import io.circe.{Decoder, Json}
import net.yoshinorin.orchard.models.db.{DeleteEvents, Events}
import net.yoshinorin.orchard.utils.Logger

class DeleteEvent(event: Events, jsonString: String) extends JsonBase[DeleteEvents] with Logger {

  val parsedJson: Json = this.parse(jsonString)

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
    val ref: Decoder.Result[String] = parsedJson.hcursor.downField("payload").get[String]("ref")
    val refType: Decoder.Result[String] = parsedJson.hcursor.downField("payload").get[String]("ref_type")

    if (ref.isRight && refType.isRight) {
      Some(DeleteEvents(event.id, event.userName, refType.right.get, ref.right.get, event.createdAt))
    } else {
      logger.error(s"Event id [${event.id}]. Failed parse to CreateEvents.")
      None
    }
  }
}

object DeleteEvent {

  def apply(event: Events, jsonString: String): DeleteEvent = new DeleteEvent(event, jsonString)

}
