package net.yoshinorin.orchard.models.db

import net.yoshinorin.orchard.models.BaseEvent

case class ForkEvents(
  eventId: Long,
  userName: String,
  forkedRepositoryId: Long,
  createdAt: Long
) extends BaseEvent[ForkEvents]
