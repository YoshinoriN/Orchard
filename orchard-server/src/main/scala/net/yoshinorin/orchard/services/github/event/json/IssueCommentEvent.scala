package net.yoshinorin.orchard.services.github.event.json

import io.circe.{Decoder, Json}
import net.yoshinorin.orchard.models.db.{Events, IssueCommentEvents}
import net.yoshinorin.orchard.utils.Logger

class IssueCommentEvent(event: Events, json: Json) extends JsonBase[IssueCommentEvents] with EventBase with Logger {

  val issueCommentEvent: Option[IssueCommentEvents] = this.convert

  /**
   * Get Converted case class
   *
   * @return
   */
  override def getConvertedCaseClass: Option[IssueCommentEvents] = this.issueCommentEvent

  /**
   * Convert JSON to IssueCommentEvent case class
   *
   * @return
   */
  override def convert: Option[IssueCommentEvents] = {
    val action: Decoder.Result[String] = json.hcursor.downField("payload").get[String]("action")
    val issueNumber: Decoder.Result[Int] = json.hcursor.downField("payload").downField("issue").get[Int]("number")

    if (action.isRight && issueNumber.isRight) {
      Some(IssueCommentEvents(event.id, event.userName, event.repositoryId, issueNumber.right.get, action.right.get, event.createdAt))
    } else {
      logger.error(s"Event id [${event.id}]. Failed parse to CreateEvents.")
      None
    }
  }
}

object IssueCommentEvent {

  def apply(event: Events, json: Json): IssueCommentEvent = new IssueCommentEvent(event, json)

}
