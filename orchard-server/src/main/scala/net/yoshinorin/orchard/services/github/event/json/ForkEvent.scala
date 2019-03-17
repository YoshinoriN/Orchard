package net.yoshinorin.orchard.services.github.event.json

import net.yoshinorin.orchard.models.db.{ForkEvents, Events}

class ForkEvent(events: Events) extends EventBase {

  val forkEvent: Option[ForkEvents] = Some(ForkEvents(events.id, events.userName, events.repositoryId, events.createdAt))

  /**
   * Get Converted case class
   *
   * @return
   */
  override def getConvertedCaseClass[ForkEvents]: Option[ForkEvents] = this.forkEvent.asInstanceOf[Option[ForkEvents]]

  /**
   * Insert to DataBase
   *
   * @tparam ForkEvents
   */
  override def insert[ForkEvents]: Unit = this.forkEvent.map { _.insert }

}

object ForkEvent {

  def apply(events: Events): ForkEvent = new ForkEvent(events)

}
