package net.yoshinorin.selfouettie.models.db

case class PullRequests(
  userName: String,
  repositoryId: Long,
  pullRequestNumber: Int,
  title: String,
  merged: Boolean
)
