package net.yoshinorin.selfouettie.models.db

import net.yoshinorin.selfouettie.models.BaseEvent

case class PushEvents(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  ref: String,
  size: Int,
  createdAt: Long
) extends BaseEvent[PushEvents]
