package net.yoshinorin.selfouettie.models

case class CreateEvents(
  eventId: Long,
  userName: String,
  ref: String,
  refType: String,
  createdAt: Long
) extends BaseEvent[CreateEvents]
