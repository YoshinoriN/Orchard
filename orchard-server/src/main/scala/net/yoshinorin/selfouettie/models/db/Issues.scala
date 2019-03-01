package net.yoshinorin.orchard.models.db

case class Issues(
  repositoryId: Long,
  issueNumber: Int,
  title: String
)
