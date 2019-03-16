package net.yoshinorin.orchard.models.db

import net.yoshinorin.orchard.models.BaseEvent

case class PushEvents(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  ref: String,
  size: Int,
  createdAt: Long
) extends BaseEvent[PushEvents] {
  override def insert(): Unit = PushEventsRepository.insert(this)
}
