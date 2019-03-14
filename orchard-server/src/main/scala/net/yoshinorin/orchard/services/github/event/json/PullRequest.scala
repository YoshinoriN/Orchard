package net.yoshinorin.orchard.services.github.event.json

import io.circe.{Decoder, Json}
import net.yoshinorin.orchard.models.db.PullRequests
import net.yoshinorin.orchard.utils.Logger

class PullRequest(repository: Repository, jsonString: String) extends JsonBase[PullRequests] with Logger {

  val parsedJson: Json = this.parse(jsonString)

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
    val number: Decoder.Result[Int] = parsedJson.hcursor.downField("payload").downField("pull_request").get[Int]("number")
    val title: Decoder.Result[String] = parsedJson.hcursor.downField("payload").downField("pull_request").get[String]("title")
    val merged: Boolean = parsedJson.hcursor.downField("payload").downField("pull_request").get[Boolean]("merged").getOrElse(false)

    if (number.isRight && title.isRight) {
      Some(PullRequests(repository.repository.get.id, number.right.get, title.right.get, merged))
    } else {
      logger.error("Can not parse PullRequest")
      None
    }
  }

}

object PullRequest {

  def apply(repository: Repository, jsonString: String): PullRequest = new PullRequest(repository, jsonString)

}
