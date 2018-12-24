package net.yoshinorin.selfouettie.models

case class Issue(
  userName: String,
  repositoryId: Long,
  issueNumber: Int,
  title: String
)
