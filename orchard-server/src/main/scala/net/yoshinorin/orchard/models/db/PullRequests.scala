package net.yoshinorin.orchard.models.db

case class PullRequests(
  repositoryId: Long,
  pullRequestNumber: Int,
  title: String,
  merged: Boolean
)
