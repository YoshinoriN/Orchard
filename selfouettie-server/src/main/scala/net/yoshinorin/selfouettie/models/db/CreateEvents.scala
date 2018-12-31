package net.yoshinorin.selfouettie.models.db

import net.yoshinorin.selfouettie.models.BaseEvent

case class CreateEvents(
  eventId: Long,
  userName: String,
  ref: String,
  refType: String,
  createdAt: Long
) extends BaseEvent[CreateEvents]
