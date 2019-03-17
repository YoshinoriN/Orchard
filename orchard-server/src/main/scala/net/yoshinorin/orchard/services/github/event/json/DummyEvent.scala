package net.yoshinorin.orchard.services.github.event.json

object DummyEvent extends EventBase {

  override def getConvertedCaseClass[T]: Option[T] = ???

  override def insert[T]: Unit = ???

}
