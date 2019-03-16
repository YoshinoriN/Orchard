package net.yoshinorin.orchard.models.db

import net.yoshinorin.orchard.models.BaseEvent

case class DeleteEvents(
  eventId: Long,
  userName: String,
  refType: String,
  ref: String,
  createdAt: Long
) extends BaseEvent[DeleteEvents] {
  override def insert(): Unit = DeleteEventsRepository.insert(this)
}
