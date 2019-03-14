package net.yoshinorin.orchard.services.github.event.json

import io.circe.Json

trait JsonBase[T] {

  def parse(jsonString: String): Json = io.circe.parser.parse(jsonString).getOrElse(Json.Null)

  def convert: Option[T]

  def getConvertedCaseClass: Option[T]

}
