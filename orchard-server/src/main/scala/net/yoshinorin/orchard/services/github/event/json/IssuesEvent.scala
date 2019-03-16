package net.yoshinorin.orchard.services.github.event.json

import io.circe.{Decoder, Json}
import net.yoshinorin.orchard.models.db.{Events, IssueEvents}
import net.yoshinorin.orchard.utils.Logger

class IssuesEvent(event: Events, jsonString: String) extends JsonBase[IssueEvents] with Logger {

  val parsedJson: Json = this.parse(jsonString)

  val issuesEvent: Option[IssueEvents] = this.convert

  /**
   * Get Converted case class
   *
   * @return
   */
  override def getConvertedCaseClass: Option[IssueEvents] = this.issuesEvent

  /**
   * Convert JSON to IssueEvents case class
   *
   * @return
   */
  override def convert: Option[IssueEvents] = {
    val action: Decoder.Result[String] = parsedJson.hcursor.downField("payload").get[String]("action")
    val issueNumber: Decoder.Result[Int] = parsedJson.hcursor.downField("payload").downField("issue").get[Int]("number")

    if (action.isRight && issueNumber.isRight) {
      Some(IssueEvents(event.id, event.userName, event.repositoryId, issueNumber.right.get, action.right.get, event.createdAt))
    } else {
      logger.error(s"Event id [${event.id}]. Failed parse to CreateEvents.")
      None
    }
  }
}

object IssuesEvent {

  def apply(event: Events, jsonString: String): IssuesEvent = new IssuesEvent(event, jsonString)

}
