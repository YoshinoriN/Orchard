package net.yoshinorin.selfouettie.models

import net.yoshinorin.selfouettie.types.EventType

case class EventObject(
  id: Long,
  eventType: EventType,
  userName: String,
  repository: Option[Repository],
  createdAt: Long,
  event: Option[BaseEvent[AnyRef]]
)
