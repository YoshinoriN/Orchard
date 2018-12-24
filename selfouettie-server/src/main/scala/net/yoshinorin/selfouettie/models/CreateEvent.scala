package net.yoshinorin.selfouettie.models

case class CreateEvent(
  eventId: Long,
  userName: String,
  ref: String,
  refType: String,
  createdAt: Long
) extends BaseEvent[CreateEvent]
