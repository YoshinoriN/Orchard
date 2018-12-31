package net.yoshinorin.selfouettie.models.db

import net.yoshinorin.selfouettie.models.BaseEvent

case class CreateEvents(
  eventId: Long,
  userName: String,
  refType: String,
  ref: String,
  createdAt: Long
) extends BaseEvent[CreateEvents]
