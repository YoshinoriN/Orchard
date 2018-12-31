package net.yoshinorin.selfouettie.models

case class ReleaseEvents(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  tagName: String,
  name: String,
  action: String,
  createdAt: Long
) extends BaseEvent[ReleaseEvents]
