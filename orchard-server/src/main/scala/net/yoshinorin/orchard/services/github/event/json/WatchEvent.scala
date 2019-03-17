package net.yoshinorin.orchard.services.github.event.json

import io.circe.{Decoder, Json}
import net.yoshinorin.orchard.models.db.{Events, WatchEvents}
import net.yoshinorin.orchard.utils.Logger

class WatchEvent(event: Events, json: Json) extends JsonBase[WatchEvents] with EventBase with Logger {

  val watchEvent: Option[WatchEvents] = this.convert

  /**
   * Get Converted case class
   *
   * @return
   */
  override def getConvertedCaseClass[WatchEvents]: Option[WatchEvents] = this.watchEvent.asInstanceOf[Option[WatchEvents]]

  /**
   * Insert to DataBase
   *
   * @tparam WatchEvents
   */
  override def insert[WatchEvents]: Unit = this.watchEvent.map { _.insert }

  /**
   * Convert JSON to WatchEvents case class
   *
   * @return
   */
  override def convert: Option[WatchEvents] = {
    val action: Decoder.Result[String] = json.hcursor.downField("payload").get[String]("action")

    if (action.isRight) {
      Some(WatchEvents(event.id, event.userName, event.repositoryId, action.right.get, event.createdAt))
    } else {
      logger.error(s"Event id [${event.id}]. Failed parse to CreateEvents.")
      None
    }
  }
}

object WatchEvent {

  def apply(event: Events, json: Json): WatchEvent = new WatchEvent(event, json)

}
