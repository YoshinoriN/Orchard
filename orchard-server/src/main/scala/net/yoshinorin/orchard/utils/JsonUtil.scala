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
  def toJsonList(s: String): Option[List[Json]] = {
    parse(s).getOrElse(None) match {
      case None => None
      case x: Json => Option(x.hcursor.values.get.toList)
    }
  }

}

object Converter {

  implicit class jsonConverter(s: String) {

    /**
     * String JSON to io.circe.JSON List
     *
     * @return
     */
    def toJson: Option[List[Json]] = {
      JsonUtil.toJsonList(s)
    }

  }

}
