package net.yoshinorin.selfouettie.models

case class PushEvents(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  ref: String,
  size: Int,
  createdAt: Long
) extends BaseEvent[PushEvents]
