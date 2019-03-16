package net.yoshinorin.orchard.services.github.event.json

import io.circe.{Decoder, Json}
import net.yoshinorin.orchard.models.db.{CreateEvents, Events}
import net.yoshinorin.orchard.utils.Logger

class CreateEvent(event: Events, json: Json) extends JsonBase[CreateEvents] with EventBase with Logger {

  val createEvent: Option[CreateEvents] = this.convert

  /**
   * Get Converted case class
   *
   * @return
   */
  override def getConvertedCaseClass: Option[CreateEvents] = this.createEvent

  /**
   * Convert JSON to CreateEvent case class
   *
   * @return
   */
  override def convert: Option[CreateEvents] = {
    val ref: Decoder.Result[String] = json.hcursor.downField("payload").get[String]("ref")
    val refType: Decoder.Result[String] = json.hcursor.downField("payload").get[String]("ref_type")

    if (ref.isRight && refType.isRight) {
      Some(CreateEvents(event.id, event.userName, refType.right.get, ref.right.get, event.createdAt))
    } else {
      logger.error(s"Event id [${event.id}]. Failed parse to CreateEvents.")
      None
    }
  }
}

object CreateEvent {

  def apply(event: Events, json: Json): CreateEvent = new CreateEvent(event, json)

}
