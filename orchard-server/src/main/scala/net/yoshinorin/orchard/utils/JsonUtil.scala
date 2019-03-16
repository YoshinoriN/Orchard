package net.yoshinorin.orchard.utils.json

import io.circe.{HCursor, Json}
import io.circe.parser.parse

object JsonUtil {

  /**
   * List of String JSON to io.circe.JSON List
   *
   * @param s
   * @return
   */
  def toJsonList(s: String): List[Json] = {
    val json = parse(s).getOrElse(Json.Null)
    val hCursor: HCursor = json.hcursor
    hCursor.values.get.toList
  }

}

object Converter {

  implicit class jsonConverter(s: String) {

    /**
     * String JSON to io.circe.JSON List
     *
     * @return
     */
    def toJson: List[Json] = {
      JsonUtil.toJsonList(s)
    }

  }

}
