package net.yoshinorin.selfouettie.models.db

import net.yoshinorin.selfouettie.models.BaseEvent

case class WatchEvents(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  action: String,
  createdAt: Long
) extends BaseEvent[WatchEvents]
