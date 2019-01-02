package net.yoshinorin.selfouettie.models.db

case class Issues(
  repositoryId: Long,
  issueNumber: Int,
  title: String
)
