package net.yoshinorin.orchard.services.github.event.json

import io.circe.{Decoder, Json}
import net.yoshinorin.orchard.models.db.{Events, PullRequestEvents}
import net.yoshinorin.orchard.utils.Logger

class PullRequestEvent(event: Events, jsonString: String) extends JsonBase[PullRequestEvents] with Logger {

  val parsedJson: Json = this.parse(jsonString)

  val pullRequestEvent: Option[PullRequestEvents] = this.convert

  /**
   * Get Converted case class
   *
   * @return
   */
  override def getConvertedCaseClass: Option[PullRequestEvents] = this.pullRequestEvent

  /**
   * Convert JSON to PullRequestEvents case class
   *
   * @return
   */
  override def convert: Option[PullRequestEvents] = {
    val action: Decoder.Result[String] = parsedJson.hcursor.downField("payload").get[String]("action")
    val pullRequestNumber: Decoder.Result[Int] = parsedJson.hcursor.downField("payload").downField("pull_request").get[Int]("number")

    if (action.isRight && pullRequestNumber.isRight) {
      Some(PullRequestEvents(event.id, event.userName, event.repositoryId, pullRequestNumber.right.get, action.right.get, event.createdAt))
    } else {
      logger.error(s"Event id [${event.id}]. Failed parse to CreateEvents.")
      None
    }
  }
}

object PullRequestEvent {

  def apply(event: Events, jsonString: String): PullRequestEvent = new PullRequestEvent(event, jsonString)

}
