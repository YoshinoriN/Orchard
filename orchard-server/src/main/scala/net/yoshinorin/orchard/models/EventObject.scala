package net.yoshinorin.orchard.models

import net.yoshinorin.orchard.models.db.{Issues, PullRequests, Repositories}
import net.yoshinorin.orchard.definitions.EventType

case class EventObject(
  id: Long,
  eventType: EventType,
  userName: String,
  repository: Repositories,
  issue: Option[Issues],
  pullRequest: Option[PullRequests],
  createdAt: Long,
  event: Option[BaseEvent[AnyRef]]
)
