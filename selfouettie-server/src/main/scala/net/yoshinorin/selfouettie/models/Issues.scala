package net.yoshinorin.selfouettie.models

case class Issues(
  repositoryId: Long,
  issueNumber: Int,
  htmlUrl: String,
  title: String
)
