package net.yoshinorin.orchard.models.db

case class Events(
  id: Long,
  eventType: String,
  userName: String,
  repositoryId: Long,
  action: String,
  url: String,
  createdAt: Long
)
