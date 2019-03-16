package net.yoshinorin.orchard.services.github.event.json

import io.circe.{Decoder, Json}
import net.yoshinorin.orchard.models.db.PullRequests
import net.yoshinorin.orchard.utils.Logger

class PullRequest(repository: Repository, json: Json) extends JsonBase[PullRequests] with Logger {

  val pullRequest: Option[PullRequests] = this.convert

  /**
   * Get Converted case class
   *
   * @return
   */
  override def getConvertedCaseClass: Option[PullRequests] = this.pullRequest

  /**
   * Convert JSON to Issues case class
   *
   * @return
   */
  override def convert: Option[PullRequests] = {
    val number: Decoder.Result[Int] = json.hcursor.downField("payload").downField("pull_request").get[Int]("number")
    val title: Decoder.Result[String] = json.hcursor.downField("payload").downField("pull_request").get[String]("title")
    val merged: Boolean = json.hcursor.downField("payload").downField("pull_request").get[Boolean]("merged").getOrElse(false)

    if (number.isRight && title.isRight) {
      Some(PullRequests(repository.repository.get.id, number.right.get, title.right.get, merged))
    } else {
      logger.error("Can not parse PullRequest")
      None
    }
  }

}

object PullRequest {

  def apply(repository: Repository, json: Json): PullRequest = new PullRequest(repository, json)

}
