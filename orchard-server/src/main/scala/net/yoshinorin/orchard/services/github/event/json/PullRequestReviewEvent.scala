package net.yoshinorin.orchard.services.github.event.json

import io.circe.{Decoder, Json}
import net.yoshinorin.orchard.models.db.{Events, PullRequestReviewEvents}
import net.yoshinorin.orchard.utils.Logger

class PullRequestReviewEvent(event: Events, jsonString: String) extends JsonBase[PullRequestReviewEvents] with Logger {

  val parsedJson: Json = this.parse(jsonString)

  val pullRequestReviewEvent: Option[PullRequestReviewEvents] = this.convert

  /**
   * Get Converted case class
   *
   * @return
   */
  override def getConvertedCaseClass: Option[PullRequestReviewEvents] = this.pullRequestReviewEvent

  /**
   * Convert JSON to PullRequestReviewEvents case class
   *
   * @return
   */
  override def convert: Option[PullRequestReviewEvents] = {
    val action: Decoder.Result[String] = parsedJson.hcursor.downField("payload").get[String]("action")
    val pullRequestNumber: Decoder.Result[Int] = parsedJson.hcursor.downField("payload").downField("pull_request").get[Int]("number")

    if (action.isRight && pullRequestNumber.isRight) {
      Some(PullRequestReviewEvents(event.id, event.userName, event.repositoryId, pullRequestNumber.right.get, action.right.get, event.createdAt))
    } else {
      logger.error(s"Event id [${event.id}]. Failed parse to CreateEvents.")
      None
    }
  }
}

object PullRequestReviewEvent {

  def apply(event: Events, jsonString: String): PullRequestReviewEvent = new PullRequestReviewEvent(event, jsonString)

}
