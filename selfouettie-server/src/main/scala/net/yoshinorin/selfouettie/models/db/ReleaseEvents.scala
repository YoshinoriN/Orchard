package net.yoshinorin.selfouettie.models.db

import net.yoshinorin.selfouettie.models.BaseEvent

case class ReleaseEvents(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  tagName: String,
  name: String,
  action: String,
  createdAt: Long
) extends BaseEvent[ReleaseEvents]
