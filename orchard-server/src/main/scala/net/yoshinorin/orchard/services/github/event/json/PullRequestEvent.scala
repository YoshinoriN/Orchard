package net.yoshinorin.orchard.services.github.event.json

import io.circe.{Decoder, Json}
import net.yoshinorin.orchard.models.db.{Events, PullRequestEvents}
import net.yoshinorin.orchard.utils.Logger

class PullRequestEvent(event: Events, json: Json) extends JsonBase[PullRequestEvents] with EventBase with Logger {

  val pullRequestEvent: Option[PullRequestEvents] = this.convert

  /**
   * Get Converted case class
   *
   * @return
   */
  override def getConvertedCaseClass[PullRequestEvents]: Option[PullRequestEvents] = this.pullRequestEvent.asInstanceOf[Option[PullRequestEvents]]

  /**
   * Insert to DataBase
   *
   * @tparam PullRequestEvents
   */
  override def insert[PullRequestEvents]: Unit = this.pullRequestEvent.map { _.insert }

  /**
   * Convert JSON to PullRequestEvents case class
   *
   * @return
   */
  override def convert: Option[PullRequestEvents] = {
    val action: Decoder.Result[String] = json.hcursor.downField("payload").get[String]("action")
    val pullRequestNumber: Decoder.Result[Int] = json.hcursor.downField("payload").downField("pull_request").get[Int]("number")

    if (action.isRight && pullRequestNumber.isRight) {
      Some(PullRequestEvents(event.id, event.userName, event.repositoryId, pullRequestNumber.right.get, action.right.get, event.createdAt))
    } else {
      logger.error(s"Event id [${event.id}]. Failed parse to PullRequestEvents.")
      None
    }
  }
}

object PullRequestEvent {

  def apply(event: Events, json: Json): PullRequestEvent = new PullRequestEvent(event, json)

}
