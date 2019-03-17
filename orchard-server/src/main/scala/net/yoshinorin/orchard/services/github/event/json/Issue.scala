package net.yoshinorin.orchard.services.github.event.json

import io.circe.{Decoder, Json}
import net.yoshinorin.orchard.models.db.{Issues, Repositories}
import net.yoshinorin.orchard.utils.Logger

class Issue(repository: Repositories, json: Json) extends JsonBase[Issues] with Logger {

  val issue: Option[Issues] = this.convert

  /**
   * Get Converted case class
   *
   * @return
   */
  def getConvertedCaseClass: Option[Issues] = this.issue

  /**
   * Convert JSON to Issues case class
   *
   * @return
   */
  override def convert: Option[Issues] = {
    val issueNumber: Decoder.Result[Int] = json.hcursor.downField("payload").downField("issue").get[Int]("number")
    val title: Decoder.Result[String] = json.hcursor.downField("payload").downField("issue").get[String]("title")

    if (issueNumber.isRight && title.isRight) {
      Some(Issues(repository.id, issueNumber.right.get, title.right.get))
    } else {
      logger.error("Can not parse Issue")
      None
    }
  }

}

object Issue {

  def apply(repository: Repositories, json: Json): Issue = new Issue(repository, json)

}
