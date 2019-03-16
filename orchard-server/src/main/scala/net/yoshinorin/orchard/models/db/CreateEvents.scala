package net.yoshinorin.orchard.models.db

import net.yoshinorin.orchard.models.BaseEvent

case class CreateEvents(
  eventId: Long,
  userName: String,
  refType: String,
  ref: String,
  createdAt: Long
) extends BaseEvent[CreateEvents] {
  override def insert(): Unit = CreateEventsRepository.insert(this)
}
