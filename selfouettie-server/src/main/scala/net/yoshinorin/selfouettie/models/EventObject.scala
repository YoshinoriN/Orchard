package net.yoshinorin.selfouettie.models

import net.yoshinorin.selfouettie.models.db.Repositories
import net.yoshinorin.selfouettie.types.EventType

case class EventObject(
  id: Long,
  eventType: EventType,
  userName: String,
  repository: Repositories,
  createdAt: Long,
  event: Option[BaseEvent[AnyRef]]
)
