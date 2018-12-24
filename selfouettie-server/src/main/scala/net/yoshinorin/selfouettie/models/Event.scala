package net.yoshinorin.selfouettie.models

case class Event(
  id: Long,
  eventType: String,
  userName: String,
  repositoryId: Long,
  action: String,
  url: String,
  createdAt: Long
)
