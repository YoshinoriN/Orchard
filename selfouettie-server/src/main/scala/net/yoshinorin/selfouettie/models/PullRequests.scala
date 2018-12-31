package net.yoshinorin.selfouettie.models

case class PullRequests(
  userName: String,
  repositoryId: Long,
  pullRequestNumber: Int,
  title: String,
  merged: Boolean
)
