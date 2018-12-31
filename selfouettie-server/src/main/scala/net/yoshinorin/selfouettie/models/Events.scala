package net.yoshinorin.selfouettie.models

import net.yoshinorin.selfouettie.types.Action.Action

case class Events(
  id: Long,
  eventType: String,
  userName: String,
  repositoryId: Long,
  action: String,
  url: String,
  createdAt: Long
)
