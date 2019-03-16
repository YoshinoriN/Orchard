package net.yoshinorin.orchard.models.db

import net.yoshinorin.orchard.models.BaseEvent

case class ReleaseEvents(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  tagName: String,
  name: String,
  action: String,
  createdAt: Long
) extends BaseEvent[ReleaseEvents] {
  override def insert(): Unit = ReleaseEventsRepository.insert(this)
}
