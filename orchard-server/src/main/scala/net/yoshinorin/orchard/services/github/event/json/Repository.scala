package net.yoshinorin.orchard.services.github.event.json

import io.circe.{Decoder, Json}
import net.yoshinorin.orchard.models.db.Repositories
import net.yoshinorin.orchard.utils.Logger

class Repository(json: Json) extends JsonBase[Repositories] with Logger {

  val repository: Option[Repositories] = this.convert

  /**
   * Get Converted case class
   *
   * @return
   */
  def getConvertedCaseClass: Option[Repositories] = this.repository

  /**
   * Convert JSON to Repository case class
   *
   * @return
   */
  override def convert: Option[Repositories] = {
    val id: Decoder.Result[Long] = json.hcursor.downField("repo").get[Long]("id")
    val name: Decoder.Result[String] = json.hcursor.downField("repo").get[String]("name")

    if (id.isRight && name.isRight) {
      Some(Repositories(id.right.get, name.right.get))
    } else {
      logger.error("Can not parse Repository.")
      None
    }
  }

}

object Repository {

  def apply(json: Json): Repository = new Repository(json)

}
