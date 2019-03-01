package net.yoshinorin.orchard.models

import net.yoshinorin.orchard.models.db.Repositories
import net.yoshinorin.orchard.types.EventType

case class EventObject(
  id: Long,
  eventType: EventType,
  userName: String,
  repository: Repositories,
  createdAt: Long,
  event: Option[BaseEvent[AnyRef]]
)
