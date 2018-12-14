package net.yoshinorin.selfouettie.models

case class PullRequests(
  repositoryId: Long,
  pullRequestNumber: Int,
  title: String,
  merged: Boolean
)
