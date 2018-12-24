package net.yoshinorin.selfouettie.models

case class PushEvent(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  ref: String,
  size: Int,
  createdAt: Long
) extends BaseEvent[PushEvent]
