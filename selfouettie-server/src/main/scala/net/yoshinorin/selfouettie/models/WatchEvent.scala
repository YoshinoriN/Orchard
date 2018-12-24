package net.yoshinorin.selfouettie.models

case class WatchEvent(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  action: String,
  createdAt: Long
)
