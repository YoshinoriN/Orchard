package net.yoshinorin.selfouettie.models

case class ForkEvent(
  eventId: Long,
  userName: String,
  forkRepositoryId: Long,
  createdAt: Long
) extends BaseEvent[ForkEvent]
