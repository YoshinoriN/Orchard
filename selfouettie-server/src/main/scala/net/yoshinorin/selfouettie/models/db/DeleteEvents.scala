package net.yoshinorin.selfouettie.models.db

import net.yoshinorin.selfouettie.models.BaseEvent

case class DeleteEvents(
  eventId: Long,
  userName: String,
  refType: String,
  ref: String,
  createdAt: Long
) extends BaseEvent[DeleteEvents]
