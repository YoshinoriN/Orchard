package net.yoshinorin.orchard.services.github.event.json

import io.circe.{Decoder, Json}
import net.yoshinorin.orchard.models.db.{Events, PushEvents}
import net.yoshinorin.orchard.utils.Logger

class PushEvent(event: Events, json: Json) extends JsonBase[PushEvents] with EventBase with Logger {

  val issuesEvent: Option[PushEvents] = this.convert

  /**
   * Get Converted case class
   *
   * @return
   */
  override def getConvertedCaseClass[PushEvents]: Option[PushEvents] = this.issuesEvent.asInstanceOf[Option[PushEvents]]

  /**
   * Insert to DataBase
   *
   * @tparam PushEvents
   */
  override def insert[PushEvents]: Unit = this.issuesEvent.map { _.insert }

  /**
   * Convert JSON to PushEvents case class
   *
   * @return
   */
  override def convert: Option[PushEvents] = {
    val ref: Decoder.Result[String] = json.hcursor.downField("payload").get[String]("ref")
    val size: Decoder.Result[Int] = json.hcursor.downField("payload").get[Int]("size")

    if (ref.isRight && size.isRight) {
      Some(PushEvents(event.id, event.userName, event.repositoryId, ref.right.get, size.right.get, event.createdAt))
    } else {
      logger.error(s"Event id [${event.id}]. Failed parse to CreateEvents.")
      None
    }
  }
}

object PushEvent {

  def apply(event: Events, json: Json): PushEvent = new PushEvent(event, json)

}
