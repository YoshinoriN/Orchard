package net.yoshinorin.selfouettie.models

case class ForkEvents(
  eventId: Long,
  userName: String,
  forkRepositoryId: Long,
  createdAt: Long
) extends BaseEvent[ForkEvents]
