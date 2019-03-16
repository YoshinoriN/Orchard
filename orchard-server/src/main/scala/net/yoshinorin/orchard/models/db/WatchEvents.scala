package net.yoshinorin.orchard.models.db

import net.yoshinorin.orchard.models.BaseEvent

case class WatchEvents(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  action: String,
  createdAt: Long
) extends BaseEvent[WatchEvents] {
  override def insert(): Unit = WatchEventsRepository.insert(this)
}
