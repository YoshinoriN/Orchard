package net.yoshinorin.selfouettie.models

case class WatchEvents(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  action: String,
  createdAt: Long
) extends BaseEvent[WatchEvents]
