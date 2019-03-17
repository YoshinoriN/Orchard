package net.yoshinorin.orchard.services.github.event.json

trait EventBase {

  def getConvertedCaseClass[T]: Option[T]

  def insert[T]: Unit

}
