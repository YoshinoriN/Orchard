package net.yoshinorin.selfouettie.models

case class PullRequest(
  userName: String,
  repositoryId: Long,
  pullRequestNumber: Int,
  title: String,
  merged: Boolean
)
