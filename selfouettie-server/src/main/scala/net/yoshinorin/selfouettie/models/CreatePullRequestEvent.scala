package net.yoshinorin.selfouettie.models

case class CreatePullRequestEvent(
  eventId: Long,
  userName: String,
  repositoryId: Long,
  pullRequestNumber: Long,
  action: String,
  createdAt: Long
)
