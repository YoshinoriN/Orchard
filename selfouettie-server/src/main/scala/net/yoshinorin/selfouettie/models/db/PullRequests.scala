package net.yoshinorin.selfouettie.models.db

case class PullRequests(
  repositoryId: Long,
  pullRequestNumber: Int,
  title: String,
  merged: Boolean
)
