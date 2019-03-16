package net.yoshinorin.orchard.services.github.event.json

import net.yoshinorin.orchard.models.db.{ForkEvents, Events}

class ForkEvent(events: Events) {

  val forkEvent: Option[ForkEvents] = Some(ForkEvents(events.id, events.userName, events.repositoryId, events.createdAt))

  /**
   * Get Converted case class
   *
   * @return
   */
  def getConvertedCaseClass: Option[ForkEvents] = this.forkEvent
}

object ForkEvent {

  def apply(events: Events): ForkEvent = new ForkEvent(events)

}
