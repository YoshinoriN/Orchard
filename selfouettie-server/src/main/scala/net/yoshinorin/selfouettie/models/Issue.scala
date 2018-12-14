package net.yoshinorin.selfouettie.models

case class Issue(
  repositoryId: Long,
  issueNumber: Int,
  htmlUrl: String,
  title: String
)
