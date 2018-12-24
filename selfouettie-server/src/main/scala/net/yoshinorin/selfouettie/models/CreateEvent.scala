package net.yoshinorin.selfouettie.models

case class CreateEvent(
  eventId: Long,
  userName: String,
  refType: String,
  ref: String,
  createdAt: Long
)
