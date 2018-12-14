package net.yoshinorin.selfouettie.models

case class PullRequest(
  repositoryId: Long,
  pullRequestNumber: Int,
  title: String,
  merged: Boolean
)
