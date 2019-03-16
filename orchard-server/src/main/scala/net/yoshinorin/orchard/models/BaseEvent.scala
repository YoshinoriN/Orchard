package net.yoshinorin.orchard.models

trait BaseEvent[+T] extends scala.AnyRef {

  def insert(): Unit

}
