package net.yoshinorin.orchard.services.github.event.json

trait JsonBase[T] {

  def convert: Option[T]

  def getConvertedCaseClass: Option[T]

}
